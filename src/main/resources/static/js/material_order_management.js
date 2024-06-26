$(document).ready(function() {
    var table = $('#example').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: false,
        info: false
    });

    var table1 = $('#example1').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: false,
        info: false

    //발주 계획 조회 버튼 클릭
    $('#openRegistrationPopup').click(function () {
        if (!table2) {
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
        }
        openPopup("registrationPopup");
    })

    $('#deliveryOk').click(function () {

        const deliveryOkOrder = []; //발주 번호를 배열형태로 저장

        //선택한 행의 정보를 배열형태로 저장
        const deliveryOk = aa.rows('.selected').data().toArray();

        console.log(deliveryOk);


        deliveryOk.forEach(function(deliveryOk) {
            console.log(deliveryOk.purchase_matarial_id); // 각 선택된 행의 order_id 내부의 orderId 속성
            deliveryOkOrder.push(deliveryOk.purchase_matarial_id);
        });

        console.log(deliveryOkOrder);

        //1.발주번호를 바탕으로 '배송중'을 '배송완료'로 변경한다.
        //2.원자재 내역 테이블에 발주번호를 등록한다.
        //3. 원자재 입고량을 구하는 방법- '주문량'(원자재발주tbl)을 가져와 '입고량'(원자재내역tbl)으로 등록한다.
        //4.dto에 현재 날짜를 등록하여 함께 저장한다.

        fetch('/api/deliveryOk', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(deliveryOkOrder)
        })
            .then(response => response.text())
            .then(data => {
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', "error");
            });


        location.reload();
        //

    });

    var table2 = $('#example2').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: false,
        info: false
    });

    var table3 = $('#example3').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: false,
        info: false
    });

    $('#deleteSelected').click(function() {
        var selectedRows = table.rows('.selected').data();
        if (selectedRows.length > 0) {
            if (confirm('선택된 항목을 삭제하시겠습니까?')) {
                selectedRows.each(function(index) {
                    var rowData = table.row(index).data();
                    console.log('삭제할 데이터:', rowData);
                });
            }
        } else {
            alert('삭제할 항목을 선택해 주세요.');
        }
    });

    $('#example').on('click', 'input[type="checkbox"]', function() {
        $(this).closest('tr').toggleClass('selected');
    });

    // 팝업 열기/닫기 함수
    window.openPopup= function(popupId) {
        $("#" + popupId).fadeIn();
    }

    window.closePopup = function(popupId) {
        $("#" + popupId).fadeOut();
    }

    // 팝업 열기 이벤트
    $(document).ready(function () {
        $("#openRegistrationPopup").click(function () {
            openPopup("registrationPopup");
        });

        $("#openConfirmationPopup").click(function () {
            closePopup("registrationPopup");
            openPopup("confirmationPopup");
        });
    });
});