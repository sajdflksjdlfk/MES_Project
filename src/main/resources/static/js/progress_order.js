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
            { data: 'used_inventory' },
            { data: 'production_quantity' },
            { data: 'order_date' },
            { data: 'expected_shipping_date' },
            { data: 'customer_name' },
            { data: 'delivery_address' },
            {
                // 삭제 버튼 칼럼 설정
                render: function (data, type, full, meta) {
                    if (full.deletable) {
                        return '<button class="btn btn-danger btn-sm delete-button">삭제</button>';
                    } else {
                        return '삭제불가능';
                    }
                }
            }
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

    // DataTables에서 생성된 테이블에 이벤트를 위임하여 삭제 버튼 클릭 처리
    $('#myTable').on('click', '.delete-button', function () {
        var data = table.row($(this).parents('tr')).data();
        handleDelete(data.order_id);
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

// 삭제 버튼 클릭 시 처리할 함수
function handleDelete(orderId) {
    if (confirm('정말로 삭제하시겠습니까?')) {
        // 여기에 삭제 처리를 수행하는 로직을 추가할 수 있습니다.
        alert('Deleted row with ID: ' + orderId);
    }
}