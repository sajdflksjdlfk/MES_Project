$(document).ready(function () {

    var table; // DataTable 객체를 저장할 변수 선언

    // 초기화 함수 정의
    function initializeDataTable(url) {
        if (table) {
            table.destroy(); // 기존 DataTable 객체가 있으면 파괴
        }

        // DataTable 초기화
        table = $('#myTable').DataTable({
            ajax: {
                url: url, // JSON 데이터 URL
                dataSrc: ''
            },
            responsive: true,
            orderMulti: true,
            columns: [
                { data: 'equipment_plan_id' },
                { data: 'product_name' },
                { data: 'quantity' },
                { data: 'estimated_start_date' },
                { data: 'estimated_end_date' },
                { data: 'status' },
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
    }

    // 셀렉트 박스 변경 이벤트 리스너
    $('#equipmentSelect').on('change', function() {
        var selectedValue = $(this).val();
        console.log(selectedValue);

        // 선택된 값에 따라 JSON 데이터 URL 변경
        var jsonUrl;
        if (selectedValue === '2') {
            jsonUrl = 'MOCK_DATA5.json'; // 선택된 값이 '2'일 때 새로운 JSON 데이터 URL
        } else {
            jsonUrl = 'MOCK_DATA2.json'; // 기본적으로 사용할 JSON 데이터 URL
        }

        // DataTable 초기화 함수 호출
        initializeDataTable(jsonUrl);
    });

    // 페이지 로드 시 초기화
    initializeDataTable('MOCK_DATA2.json'); // 초기에는 기본 데이터로 초기화
});
