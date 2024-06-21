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

                // 시간대로 변환하여 출력
                var startDateKoreanTime = convertToKoreanTime(equipmentDto.estimatedStartDate);
                var endDateKoreanTime = convertToKoreanTime(equipmentDto.estimatedEndDate);

                console.log("설비번호: " + equipmentDto.equipmentId);
                console.log("착즙기 시작 예상 시간: " + startDateKoreanTime);
                console.log("착즙기 종료 예상 시간: " + endDateKoreanTime);
                console.log("착즙기 투입량: " + equipmentDto.input);
                console.log("착즙기 산출량: " + equipmentDto.output);

                // 예상 시작 날짜를 설정
                id34StartDate = equipmentDto.estimatedStartDate;

                // 예상 종료 날짜를 설정
                id34EndDate = equipmentDto.estimatedEndDate;
                
                // 여과기 투입량 계산
                id9Input = equipmentDto.output * 1000;

                // equipmentDto.input을 0.75로 나눈 후 올림하여 id2Input에 저장
                var id2Input = Math.ceil(equipmentDto.input / 0.75);

                // id2 세척 계획 잡기
                id2Plan(id34StartDate, id2Input);

                // id9 여과기 계획 잡기
                id9Plan(id34EndDate, id9Input);
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

        // 한국 표준시(KST, GMT+09:00)로 변환
        var koreanTimezoneOffset = 9 * 60; // 한국 표준시의 UTC 오프셋 (분 단위)
        var localTimezoneOffset = cleaningStartDateTime.getTimezoneOffset(); // 현재 지역의 UTC 오프셋 (분 단위)
        var utcTime = cleaningStartDateTime.getTime() + (localTimezoneOffset * 60000); // 현재 시간을 UTC로 변환
        var koreanTime = new Date(utcTime + (koreanTimezoneOffset * 60000)); // 한국 표준시로 변환된 시간 객체

        // 한국 표준시 기준으로 9시간을 더한 ISO 8601 문자열 생성
        var isoStringWithKoreanTime = new Date(koreanTime.getTime() + (9 * 60 * 60 * 1000)).toISOString();

        // AJAX 요청을 통해 데이터를 백엔드로 전송하고 응답을 받음
        $.ajax({
            url: '/api/id2Plan',
            type: 'GET',
            data: {
                id2Input: id2Input,
                id2Output: id2Output,
                cleaningTimeMinutes: cleaningTimeMinutes,
                cleaningStartDateTime: isoStringWithKoreanTime // 한국 표준시 기준으로 9시간을 더한 ISO 문자열 전송
            },
            success: function (data) {
                // 세척 계획 시작 시간을 한국 시간대로 변환하여 출력
                var startDateKoreanTime = convertToKoreanTime(data.estimatedStartDate);
                // 세척 계획 종료 시간을 한국 시간대로 변환하여 출력
                var endDateKoreanTime = convertToKoreanTime(data.estimatedEndDate);

                // 응답 데이터 콘솔 출력
                console.log("설비 번호: " + data.equipmentId);
                console.log("세척 계획 시작 시간: " + startDateKoreanTime);
                console.log("세척 계획 종료 시간: " + endDateKoreanTime);
                console.log("세척 공정 투입량: " + data.input);
                console.log("세척 공정 산출량: " + data.output);

                id2StartDate = data.estimatedStartDate;
                id1Input = data.input;

                // 발주 계획 잡기
                id1Plan(id2StartDate, id1Input);
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    // 발주 계획 잡기
    function id1Plan(id2StartDate, id1Input) {

        var id1EndDate; // 발주 종료 시간을 저장할 변수

        // 세척 공정 시작 시간(id2StartDate)을 Date 객체로 변환
        var startDate = new Date(id2StartDate);
        startDate.setTime(startDate.getTime() + (9 * 60 * 60 * 1000)); // 9시간을 더함

        // 세척 공정 시작 날짜의 오전 9시 시간
        var cleaningStart9AM = new Date(startDate);
        cleaningStart9AM.setHours(9, 0, 0, 0); // 세척 공정 시작일의 09:00:00

        // 세척 공정 시작 날짜의 1일 전 오전 9시 시간
        var cleaningStartYesterday9AM = new Date(cleaningStart9AM);
        cleaningStartYesterday9AM.setDate(cleaningStart9AM.getDate() - 1); // 전날 09:00:00

        // 설비2가 시작하는 시간이 오늘 오전 9시 이전이면
        if (startDate < cleaningStart9AM) {
            id1EndDate = new Date(cleaningStartYesterday9AM.getTime()); // 전날 09:00:00에서
        } else {
            id1EndDate = new Date(cleaningStart9AM.getTime()); // 오늘 09:00:00에서
        }

        // 발주 시작 시간은 발주 종료 시간에서 1일 21시간 후의 시간으로 설정
        var id1StartDate = new Date(id1EndDate.getTime() - (24 * 60 * 60 * 1000) - (21 * 60 * 60 * 1000));

        // DTO 구성
        var equipmentDto = {
            equipmentId: 1, // 장비 ID는 1로 설정
            estimatedStartDate: id1StartDate,
            estimatedEndDate: id1EndDate,
            input: 0, // 발주는 input이 없으므로 0
            output: id1Input // 발주 산출량 설정
        };

        // 콘솔에 출력
        console.log("발주 계획");
        console.log("설비 번호: " + equipmentDto.equipmentId);
        console.log("발주 시작 시간: " + equipmentDto.estimatedStartDate);
        console.log("발주 종료 시간: " + equipmentDto.estimatedEndDate);
        console.log("발주 산출량: " + equipmentDto.output);
    }

    // id9 여과기 계획 잡기
    function id9Plan(id34EndDate, id9Input) {
        // AJAX 요청 설정
        $.ajax({
            url: '/api/id9Plan',
            type: 'GET',
            data: {
                id34EndDate: id34EndDate,
                id9Input: id9Input
            },
            success: function(data) {
                // 성공적으로 데이터를 받았을 때 실행되는 함수
                console.log("여과기 계획");
                console.log("설비 번호: " + data.equipmentId);
                console.log("여과기 시작 시간: " + convertToKoreanTime(data.estimatedStartDate));
                console.log("여과기 종료 시간: " + convertToKoreanTime(data.estimatedEndDate));
                console.log("여과기 투입량: " + data.input);
                console.log("여과기 산출량: " + data.output);

                id56EndDate = data.estimatedEndDate;
                id56Input = data.output;

                id56Plan(id56EndDate, id56Input);
            },
            error: function(xhr, status, error) {
                // AJAX 요청 중 에러가 발생했을 때 실행되는 함수
                console.error('AJAX Error:', status, error);
            }
        });
    }

    // id56 살균기 계획 잡기
    function id56Plan(id56EndDate, id56Input) {
        // AJAX 요청 설정
        $.ajax({
            url: '/api/id56Plan',
            type: 'GET',
            data: {
                id56EndDate: id56EndDate,
                id56Input: id56Input
            },
            success: function(data) {
                // 성공적으로 데이터를 받았을 때 실행되는 함수
                console.log("살균기 계획");
                console.log("설비 번호: " + data.equipmentId);
                console.log("살균기 시작 시간: " + convertToKoreanTime(data.estimatedStartDate));
                console.log("살균기 종료 시간: " + convertToKoreanTime(data.estimatedEndDate));
                console.log("살균기 투입량: " + data.input);
                console.log("살균기 산출량: " + data.output);
            },
            error: function(xhr, status, error) {
                // AJAX 요청 중 에러가 발생했을 때 실행되는 함수
                console.error('AJAX Error:', status, error);
            }
        });
    }

    // UTC를 한국 시간대로 변환하는 함수
    function convertToKoreanTime(utcDateTime) {
        var date = new Date(utcDateTime);
        var koreanTime = new Date(date.getTime() + (9 * 60 * 60 * 1000)); // 9시간을 밀리초로 계산하여 더함
        return koreanTime.toLocaleString('ko-KR', { timeZone: 'Asia/Seoul' });
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
