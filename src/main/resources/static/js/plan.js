$(document).ready(function () {
    // 주문수량 또는 재고사용 입력 필드의 값이 변경될 때마다 예상출하날짜를 계산
    $('#productName, #orderQuantity, #stockUsage').on('input change', function () {
        calculateExpectedShipmentDate();
    });

    $('#confirm').on('click', function () {
        saveAllPlans();
    });

    function calculateExpectedShipmentDate() {
        var productName = $('#productName').val();
        var orderQuantity = parseInt($('#orderQuantity').val()) || 0; // 숫자로 파싱하며, 실패할 경우 0으로 설정
        var stockUsage = parseInt($('#stockUsage').val()) || 0; // 숫자로 파싱하며, 실패할 경우 0으로 설정
        var productionQuantity = Math.ceil((orderQuantity - stockUsage) * 1.031); // 1.031을 곱해서 올림 처리


        if (!isNaN(productionQuantity)) {
            $.ajax({
                url: '/api/calculateProductionQuantity',
                type: 'GET',
                data: {
                    productName: productName,
                    productionQuantity: productionQuantity
                },
                success: function (data) {
                    var numberOfPlans = data[0];
                    var totalProductionQuantity = data[1];

                    var newPlan = numberOfPlans; // 새로운 계획을 새워야 하는지 여부
                    var quantityPlan = totalProductionQuantity; // 실제작수량
                    // 각 값 콘솔에 출력
                    console.log("만들어야 하는 생산계획 개수: " + numberOfPlans);
                    console.log("마지막 생산계획에 넣어야하는 수량: " + totalProductionQuantity);

                    var id34Input = totalProductionQuantity * 4 * 0.75;

                    // 생산계획 1개 만들때 착즙기 시간 계산
                    if (newPlan == 1) {
                        id34Plan(3, 4, id34Input); // 설비 ID를 3과 4로 가정
                    }
                },
                error: function (xhr, status, error) {
                    console.error('에러 발생:', error);
                }
            });
        } else {
            console.log("유효하지 않은 생산 수량입니다.");
        }

    }

    // 착즙기 선택 메서드
    function id34Plan(equipmentId3, equipmentId4, id34Input) {
        $.ajax({
            url: '/api/earliestEndDate',
            type: 'GET',
            data: {
                equipmentId3: equipmentId3,
                equipmentId4: equipmentId4,
                input: id34Input
            },
            success: function (data) {
                // DTO를 컨트롤러로 전달하고, 콘솔에 출력
                var equipmentDto = data; // 예상되는 DTO 형식에 맞게 데이터를 가공

                console.log("설비번호: " + equipmentDto.equipmentId);
                console.log("착즙기 시작 예상 시간: " + equipmentDto.estimatedStartDate);
                console.log("착즙기 종료 예상 시간: " + equipmentDto.estimatedEndDate);
                console.log("착즙기 투입량: " + equipmentDto.input);
                console.log("착즙기 산출량: " + equipmentDto.output);

                // 예상 시작 날짜를 설정
                id34StartDate = equipmentDto.estimatedStartDate;

                // equipmentDto.input을 0.75로 나눈 후 올림하여 id2Input에 저장
                var id2Input = Math.ceil(equipmentDto.input / 0.75);

                // id2Plan 함수 호출 시 id34StartDate 전달
                id2Plan(id34StartDate, id2Input);
            },
            error: function (xhr, status, error) {
                console.error('에러 발생:', error);
            }
        });
    }

    // 세척 계획 잡기
    function id2Plan(id34StartDate, id2Input) {
        // 산출량 계산
        var id2Output = Math.floor(id2Input * 0.75);
        // 세척 공정에 걸리는 시간 계산
        var cleaningTimeHours = (id2Input / 1000) * 2; // 1000 투입량당 2시간
        var cleaningTimeMinutes = Math.ceil(cleaningTimeHours * 60); // 시간을 분으로 변환하여 올림 처리

        // id34StartDate를 LocalDateTime으로 변환
        var id34StartDateTime = new Date(id34StartDate);

        // 세척 공정 시작 시간을 계산 (id34StartDateTime에서 cleaningTimeMinutes를 빼기)
        var cleaningStartDateTime = new Date(id34StartDateTime.getTime() - cleaningTimeMinutes * 60000);

        // 10분 단위로 내림 처리
        var minutes = cleaningStartDateTime.getMinutes();
        cleaningStartDateTime.setMinutes(minutes - minutes % 10);
        cleaningStartDateTime.setSeconds(0);
        cleaningStartDateTime.setMilliseconds(0);

        console.log("세척 공정 투입량: " + id2Input);
        console.log("세척 공정 투입량: " + id2Output);
        console.log("세척 공정에 필요한 시간(분): " + cleaningTimeMinutes);
        console.log("세척 공정 시작 시간: " + cleaningStartDateTime.toLocaleString());
    }


    function saveAllPlans() {
        $.ajax({
            url: '/api/saveAllPlans',
            type: 'POST',
            success: function () {
                console.log("계획이 성공적으로 저장되었습니다.");
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }
});
