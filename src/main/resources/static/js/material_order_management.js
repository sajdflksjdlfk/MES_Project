// // 전역 변수와 초기 객체 선언
// var selectedData = [];
// var aggregatedQuantities = {};
//
// var newObject = {
//     '벌꿀': 0,
//     '양배추': 0,
//     '흑마늘': 0,
//     '석류농축액': 0,
//     '매실농축액': 0,
//     '콜라겐': 0
// };
var table2;
var table3; // 전역 변수로 선언
var table4;
var allData;    //발주 등록의 모든 수주 데이터
// 양배추즙 1개당 필요한 재료의 양
const CABBAGEorGarlic_PER_JUICE = 4; // 1 양배추즙 당 1kg 양배추
const HONEY_PER_JUICE = 0.15; // 1 양배추즙 당 0.1kg 벌꿀
const PER_JUICE = 150; // ML
const COLAGEN_PER_JUICE = 5; // ML

var selectedRows=[];


const result = [];// 수주번호 + 주문해야할 원자재의 배열 값, DB에 저장할 값



$(document).ready(function() {

    //원자재 발주 관리 페이지 테이블
    const aa = $('#example').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: {
            style: 'multi'
        },
        info: false,
        ajax: {
            url: '/api/delivered',
            dataSrc: 'data',
            type: 'GET'
        },
        responsive: true,
        orderMulti: true,
        columns: [
            {data: 'order_id'},
            {data: 'product_name'},
            {data: 'product_name'},
            {data: 'production_quantity'},
            {data: 'expected_shipping_date'},
        ]
    });


    //발주 계획 조회 버튼 클릭
    $('#openRegistrationPopup').click(function () {

        //발주 계획 페이지 테이블
        table2 = $('#example1').DataTable({
            paging: false,
            lengthChange: false,
            searching: false,
            select: {
                style: 'multi'
            },
            info: false,
            ajax: {
                url: '/api/orderPlan',
                dataSrc: 'data',
                type: 'GET'
            },
            responsive: true,
            orderMulti: true,
            columns: [
                {data: 'order_id'},
                {data: 'product_name'},
                {data: 'production_quantity'},
                {data: 'expected_shipping_date'},
            ]
        });
        openPopup("registrationPopup");
    })


//     //체크박스 클릭 시 이벤트
//     $('#example1').on('click', '.checkbox', function() {
//
//         //1.가장 가까운 행의 정보를 rowData에 저장한다.
//         var rowData = table2.row($(this).closest('tr')).data();
//
//         // 2.체크박스가 체크되었다면 선택된 데이터를 selectedData에 추가한다
//         if ($(this).is(':checked')) {
//             selectedData.push(rowData); // 선택된 데이터를 리스트에 추가
//
//         // 3. 체크가 해제된 경우 [is (:checked)]가 아닌 경우에 else 구문 실행
//         //    data(selectedData)와 rowData가 같으면 false리턴
//         //    => false가 리턴된 정보에 대해서는 selectedData에서 삭제됨
//         } else {
//             selectedData = selectedData.filter(function(data) {
//                 return data.order_id !== rowData.order_id;
//             });
//         }
//     });



    //발주버튼 클릭 이벤트
    $("#openConfirmationPopup").click(function () {

        //선택된 행의 데이터를 배열형태로 저장/
        //forEach함수 사용을 위해 배열형대로 변환
        selectedRows = table2.rows('.selected').data().toArray();


        if (selectedRows.length > 0) {
            if (confirm('선택된 항목을 발주하시겠습니까?')) {

                //map = 배열의 각 요소에 대해 주어진 함수를 호출하여 그 결과로 새로운 배열을 생성
                // //selectedRows를 가공하여 order_id 값을 가지는 배열로 생성
                // orderIds = selectedRows.map(function(rowData) {
                //     return rowData.order_id;
                // });

                closePopup("registrationPopup");
                openPopup2("confirmationPopup");

            }
        } else {
            alert('발주할 항목을 선택해 주세요.');
        }


    });



    //1번째 팝업 오픈 매서드
    window.openPopup = function (popupId) {
        $("#" + popupId).fadeIn();
    }

    //2번째 팝업 오픈 매서드
    //orderIds에 해당하는 값을 출력
    window.openPopup2 = function (popupId) {
        $("#" + popupId).fadeIn();
        if (!table3) {
            table3 = $('#example2').DataTable({
                lengthChange: false,
                paging: false,
                searching: false,
                select: {
                    style: 'multi'
                },
                info: false,
                data: selectedRows,
                responsive: true,
                orderMulti: true,
                columns: [
                    {data: 'order_id'},
                    {data: 'product_name'},
                    {data: 'production_quantity'},
                ],

            })
        }

        divideOrders(selectedRows);
        console.log(result[0]);
        console.log(result[1]);


    }


    window.closePopup = function (popupId) {
        $("#" + popupId).fadeOut();
    }

    // function processOrderData(allData) {
    //     allData.forEach(function(index) {
    //         // 수주번호
    //         var orderId = index.order_id;
    //         var product_name = index.product_name;
    //         var production_quantity = index.production_quantity;


    function divideOrders(selectedRows) {
        selectedRows.forEach(function (rowData) {

            var orderId = rowData.order_id;
            var product_name = rowData.product_name;
            var production_quantity = rowData.production_quantity;

            console.log(orderId);
            console.log(product_name);
            console.log(production_quantity);



            //양배추즙일 경우
            if('양배추즙'===product_name){
                console.log("양배추즙")

                // 주문해야 할 양배추와 벌꿀의 양 계산
                var cabbageRequired = production_quantity * CABBAGEorGarlic_PER_JUICE;
                var honeyRequired = production_quantity * HONEY_PER_JUICE

                // 객체로 만들어 배열에 추가
                const data1 = {
                    order_id: orderId,
                    product: '양배추',
                    quantity: cabbageRequired
                };

                const data2 = {
                    order_id: orderId,
                    product: '벌꿀',
                    quantity: honeyRequired
                };

                result.push(data1);
                result.push(data2);



            //흑마늘즙일 경우
            }else if('흑마늘즙'===product_name) {
                console.log("흑마늘즙")

                // 주문해야 할 양배추와 벌꿀의 양 계산
                var garlicRequired = production_quantity * CABBAGEorGarlic_PER_JUICE;
                var honeyRequired2 = production_quantity * HONEY_PER_JUICE

                // 객체로 만들어 배열에 추가
                const data1 = {
                    order_id: orderId,
                    product: '흑마늘',
                    quantity: garlicRequired
                };

                const data2 = {
                    order_id: orderId,
                    product: '벌꿀',
                    quantity: honeyRequired2
                };

                result.push(data1);
                result.push(data2);



            //석류 젤리일 경우
            }else if('석류젤리'===product_name) {
                console.log("석류젤리")

                // 주문해야 할 양배추와 벌꿀의 양 계산
                var pomegranateJelly = production_quantity * PER_JUICE;
                var collagenRequired = production_quantity * COLAGEN_PER_JUICE

                // 객체로 만들어 배열에 추가
                const data1 = {
                    order_id: orderId,
                    product: '석류농축액',
                    quantity: pomegranateJelly
                };

                const data2 = {
                    order_id: orderId,
                    product: '콜라겐',
                    quantity: collagenRequired
                };

                result.push(data1);
                result.push(data2);

            //매실 젤리인 경우
            }else if('매실젤리'===product_name) {
                console.log("매실젤리")

                // 주문해야 할 양배추와 벌꿀의 양 계산
                var plumJelly = production_quantity * PER_JUICE;
                var collagenRequired2 = production_quantity * COLAGEN_PER_JUICE

                // 객체로 만들어 배열에 추가
                const data1 = {
                    order_id: orderId,
                    product: '석류농축액',
                    quantity: plumJelly
                };

                const data2 = {
                    order_id: orderId,
                    product: '콜라겐',
                    quantity: collagenRequired2
                };

                result.push(data1);
                result.push(data2);
            }

            // console.log(result[0]);
            // console.log(result[1]);
            // console.log(result[3]);
            // console.log(result[4]);
            // console.log(result[5]);
            // console.log(result[6]);
            // console.log(result[7]);
            // console.log(result[8]);

        });

        if (!table4) {
            table4 = $('#example4').DataTable({
                lengthChange: false,
                paging:false,
                searching: false,
                select: false,
                info: false,
                data: result,
                responsive: true,
                orderMulti: true,
                columns: [
                    {data: 'order_id'},
                    {data: 'product'},
                    {data: 'quantity'},
                ],
                // initComplete: function() {
                //     // 테이블 초기화가 완료된 후 데이터 처리
                //     var allData = table3.rows().data().toArray(); // 데이터를 배열로 변환
                //     processOrderData(allData); // 데이터 처리 함수 호출
                // }

            })
        }


    }
})



    //값을 바탕으로 양배추 벌꿀 흑마늘...의 양을 매개변수로 받는 테이블 생성 함수 만들기.

    //등록하기 버튼 클릭 시 DB 저장하기.
