$(document).ready(function () {
    // 주문수량 또는 재고사용 입력 필드의 값이 변경될 때마다 예상출하날짜를 계산
    $('#productName, #orderQuantity, #stockUsage').on('input change', function () {
        calculateExpectedShipmentDate();
    });

    function calculateExpectedShipmentDate() {
        var productName = $('#productName').val();
        var orderQuantity = parseInt($('#orderQuantity').val());
        var stockUsage = parseInt($('#stockUsage').val());
        var productionQuantity = Math.ceil((orderQuantity - stockUsage) * 1.031); // 1.031을 곱해서 올림 처리
        var newPlan = 0; // 새로운 계획을 새워야 하는지 여부
        var quantityPlan = 0; // 실제작수량

        var additionalDays;

        if (!isNaN(productionQuantity) && productName === "양배추즙") {
            // 원자재 발주를 진행하지 않은 생산계획 중 제품명이 같은 생산계획이 있는가?
            $.ajax({
                url: '/api/findLatestPlanByProductName', // 해당 API 엔드포인트로 수정해야 함
                type: 'GET',
                data: {
                    productName: productName
                },
                success: function(response) {
                    if (response && response.planned_quantity !== undefined && response.planned_quantity !== null && response.planned_quantity !== "") {
                        var latestPlanQuantity = response.planned_quantity || 0;
                        // 총 제작수량이 한번에 생산할 수 있는 수량보다 크거나 같은 경우
                        if (latestPlanQuantity + productionQuantity > 333) {
                            console.log("제작수량 + 최근 계획 수량 > 333. 새로운 생산계획을 만들어야 함. 실제작수량:", productionQuantity);
                            newPlan = 1;
                            quantityPlan = productionQuantity;
                        } else {
                            // 총 제작수량이 한번에 생산할 수 있는 수량보다 작거나 같은 경우
                            console.log("제작수량 + 최근 계획 수량 <= 333. 현재 생산계획 사용. 실제작수량:", latestPlanQuantity + productionQuantity);
                            quantityPlan = latestPlanQuantity + productionQuantity;
                        }
                    } else {
                        // 새로운 생산계획을 만들어야 함
                        console.log("새로운 생산계획을 만들어야 함. 실제작수량:", productionQuantity);
                        newPlan = 1;
                        quantityPlan = productionQuantity;
                    }

                    // 여기서 newPlan 변수를 기반으로 새로운 계획을 만들어야 할지, 기존 계획에 추가해야 할지 판별
                    if(newPlan == 1){
                        while (quantityPlan > 333) {
                            quantityPlan -= 333;
                            newPlan ++;
                        }
                    }else{
                        console.log("기존 계획에 추가")
                    }

                    console.log("만들어야 하는 생산계획 개수 : " + newPlan);
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                }
            });
            
            

            // true 총 제작수량이 한번에 생산할 수 있는 수량인가? false 새로운 생산계획을 만든다. 실제작수량 = 제작수량 * 1.031 에 올림한 수량



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