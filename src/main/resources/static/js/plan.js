$(document).ready(function () {
    // 주문수량 또는 재고사용 입력 필드의 값이 변경될 때마다 예상출하날짜를 계산
    $('#productName, #orderQuantity, #stockUsage').on('input change', function () {
        calculateExpectedShipmentDate();
    });

    function calculateExpectedShipmentDate() {
        var productName = $('#productName').val();
        var orderQuantity = parseInt($('#orderQuantity').val());
        var stockUsage = parseInt($('#stockUsage').val());
        var productionQuantity = orderQuantity - stockUsage;

        var additionalDays;

        if (!isNaN(productionQuantity) && productName === "양배추즙") {
            // 조건이 만족될 때 실행할 코드
            additionalDays = 1;
        } else if (!isNaN(productionQuantity) && productName === "흑마늘즙") {
            additionalDays = 2;
        } else if (!isNaN(productionQuantity) && productName === "석류젤리") {
            additionalDays = 3;
        } else if (!isNaN(productionQuantity) && productName === "매실젤리") {
            additionalDays = 4;
        }

        if(!isNaN(additionalDays)){
            var today = new Date();
            today.setDate(today.getDate() + additionalDays);
            var expectedShipmentDate = today.toISOString().split('T')[0];
            $('#expectedShipmentDate').val(expectedShipmentDate);
        }
    }
});