$(document).ready(function() {
    var tableIds = ['#example', '#example2'];

    $.each(tableIds, function(index, tableId) {
        $(tableId).DataTable({
            paging: false,
            lengthChange: false,
            searching: false,
            select: false,
            info: false
        });
    });

    // 출하 버튼 클릭 이벤트 처리
    $('.btn-ship').click(function() {
        var $row = $(this).closest('tr'); // 클릭된 버튼의 부모 행을 선택합니다.
        var rowData = $row.find('td').map(function() {
            return $(this).text(); // 선택된 행의 데이터를 배열로 가져옵니다.
        }).get();

        // 출하 이력 테이블에 행 추가
        $('#example2').find('tbody').append('<tr>' +
            '<td>' + rowData[1] + '</td>' +
            '<td>' + rowData[2] + '</td>' +
            '<td>' + rowData[3] + '</td>' +
            '<td>' + rowData[4] + '</td>' +
            '<td>' + rowData[5] + '</td>' +
            '<td>' + rowData[6] + '</td>' +
            '<td>' + new Date().toLocaleDateString() + '</td>' +
            '</tr>');

        // 선택된 행 삭제
        $row.remove();
    });
});