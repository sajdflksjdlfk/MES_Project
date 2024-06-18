$(document).ready(function () {
    var table = $('#myTable').DataTable({
        ajax: {
            url: 'MOCK_DATA.json',
            dataSrc: ''
        },
        responsive: true,
        orderMulti: true,
        columns: [
            { data: 'order_id' },
            { data: 'product_name' },
            { data: 'quantity' },
            { data: 'order_date' },
            { data: 'expected_shipping_date' },
            { data: 'customer_name' },
            { data: 'delivery_address' }
        ],
        "language": {
            "emptyTable": "데이터가 없어요.",
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
            "info": "현재 _START_ - _END_ / _TOTAL_건",
            "infoEmpty": "데이터 없음",
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 데이터가 없어요.",
            "loadingRecords": "로딩중...",
            "processing": "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                text: 'Export Excel',
                className: 'exportCSV',
                filename: 'exported_data',
                exportOptions: {
                    columns: ':visible'
                },
                customize: function (xlsx) {
                    var sheet = xlsx.xl.worksheets['sheet1.xml'];
                    $('row c', sheet).attr('s', '25');
                }
            },
            {
                extend: 'colvis',
                text: '열 선택',
                className: 'colVisButton',
                columns: ':not(.no-export)'
            }
        ]
    });

    // 열 선택 버튼 클릭 시 이벤트 처리
    $('#myTable').on('click', '.colVisButton', function() {
        table.buttons(['.buttons-colvis']).trigger();
    });

});

// 팝업 열기/닫기 함수
function openPopup(popupId) {
    $("#" + popupId).fadeIn();
}

function closePopup(popupId) {
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