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