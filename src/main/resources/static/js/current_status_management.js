<!-- dataTable 초기화/설정 -->
    $(document).ready( function () {
    $('#myTable1').DataTable({
        "searching": false,     // 검색 상자 비활성화
        "paging": false,        // 페이지네이션 비활성화
        "ordering": false       // 정렬 기능 비활성화
    });
    $('#myTable2').DataTable({
    "searching": false,     // 검색 상자 비활성화
    "lengthChange": false   // Entries per page 드롭다운 비활성화
});

    // "Showing X entries" 메시지 숨기기
    $('#myTable1_info').hide();  // myTable1의 "Showing X entries" 메시지 숨기기
    $('#myTable2_info').hide();  // myTable2의 "Showing X entries" 메시지 숨기기

    // DataTables 초기화 후 width 속성을 100%로 변경
    $('#myTable1_wrapper').find('table').css({
    'width': '100%',
    'margin': '0',
    'padding': '3px'
});
    $('#myTable2_wrapper').find('table').css({
    'width': '100%',
    'margin': '0',
    'padding': '3px'
});
} );

    // 새로고침 버튼 클릭 시 DataTable1 다시 로드
    function refreshTable1() {
    $('#myTable1').DataTable().ajax.reload();
}


<!-- Chart.js 초기화/설정 -->

    const ctx1 = document.getElementById('myChart1');
    const ctx2 = document.getElementById('myChart2');

    // Chart1: 일일 생산량과 월간 생산량
    const myChart1 = new Chart(ctx1, {
    type: 'bar',
    data: {
    labels: ['일일 생산량'],
    datasets: [{
    label: '생산량',
    data: [0],
    backgroundColor: ['rgba(54, 162, 235, 0.2)'],
    borderColor: ['rgba(54, 162, 235, 1)'],
    borderWidth: 1
}]
},
    options: {
    scales: {
    y: {
    beginAtZero: true
}
}
}
});

    <!-- 일일 생산량 데이터 가져오는 부분 -->
    document.getElementById('dailyButton').addEventListener('click', function() {
    fetch('/api/dailyProduction')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => formatDate(item.received_date));  // 날짜 데이터 변환
            const values = data.map(item => item.received_quantity);  // 생산 데이터
            myChart1.data.labels = labels;
            myChart1.data.datasets[0].data = values;
            myChart1.data.datasets[0].backgroundColor = 'rgba(54, 162, 235, 0.2)';
            myChart1.data.datasets[0].borderColor = 'rgba(54, 162, 235, 1)';
            myChart1.update();

        })
});

    function formatDate(dateString) {
    const date = new Date(dateString);
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}

    <!-- 월간 생산량 데이터 가져오는 부분 -->
    document.getElementById('monthlyButton').addEventListener('click', function() {
    fetch('/api/monthlyProduction')
        .then(response => response.json())
        .then(data => {
            const labels = Object.keys(data);  // 월별 레이블 가져오기
            const values = Object.values(data);  // 월간 생산량 데이터 가져오기

            // Chart.js 데이터 업데이트
            myChart1.data.labels = labels;
            myChart1.data.datasets[0].data = values;
            myChart1.data.datasets[0].backgroundColor = 'rgba(255, 99, 132, 0.2)';
            myChart1.data.datasets[0].borderColor = 'rgba(255, 99, 132, 1)';
            myChart1.update();
        });
});

    // Chart2: 생산실적 백분율
    const myChart2 = new Chart(ctx2, {
    type: 'bar',
    data: {
    labels: [], // 레이블은 비어있음
    datasets: [{
    label: '투입량 대비 산출량 (%)',
    backgroundColor: 'rgba(54, 162, 235, 0.2)',
    borderColor: 'rgba(54, 162, 235, 1)',
    borderWidth: 1,
    data: [], // 초기 데이터는 비어있음
}]
},
    options: {
    scales: {
    y: {
    beginAtZero: true,
    ticks: {
    callback: function(value) {
    return value + '%'; // y축에 퍼센트 표시 추가
}
}
}
}
}
});

    // 생산실적 데이터 가져오기
    fetch('/api/performance')
    .then(response => response.json())
    .then(data => {
    const labels = data.map(item => `주문 ${item.order_id}`); // 수주번호를 레이블로 사용
    const percentages = calculatePercentages(data); // 백분율 계산

    // Chart.js 데이터 업데이트
    myChart2.data.labels = labels;
    myChart2.data.datasets[0].data = percentages;
    myChart2.update();
});

    // 백분율 계산 함수
    function calculatePercentages(data) {
    const percentages = [];
    data.forEach(item => {
    const percentage = (item.output / item.input) * 100;
    percentages.push(Math.round(percentage * 100) / 100); // 소수점 둘째 자리까지 반올림
});
    return percentages;
}