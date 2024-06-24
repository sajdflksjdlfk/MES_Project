// 전역 변수와 초기 객체 선언
var selectedData = [];
var aggregatedQuantities = {};

var newObject = {
    '벌꿀': 0,
    '양배추': 0,
    '흑마늘': 0,
    '석류농축액': 0,
    '매실농축액': 0,
    '콜라겐': 0
};

var table3; // 전역 변수로 선언

$(document).ready(function() {
    const table = $('#example').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: false,
        info: false,
        ajax: {
            url: '/api/delivered',
            dataSrc: 'data',
            type: 'GET'
        },
        responsive: true,
        orderMulti: true,
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    return '<input type="checkbox" class="checkbox" data-order-id="' + data.order_id + '">';
                }
            },
            {data: 'order_id'},
            {data: 'product_name'},
            {data: 'product_name'},
            {data: 'production_quantity'},
            {data: 'expected_shipping_date'},
        ]
    });

    const table2 = $('#example1').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        select: true,
        info: false,
        ajax: {
            url: '/api/orderPlan',
            dataSrc: 'data',
            type: 'GET'
        },
        responsive: true,
        orderMulti: true,
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    return '<input type="checkbox" class="checkbox" data-order-id="' + data.order_id + '">';
                }
            },
            {data: 'order_id'},
            {data: 'product_name'},
            {data: 'production_quantity'},
            {data: 'expected_shipping_date'},
        ]
    });

    $('#example1').on('click', '.checkbox', function() {
        var rowData = table2.row($(this).closest('tr')).data();
        if ($(this).is(':checked')) {
            selectedData.push(rowData); // 선택된 데이터를 리스트에 추가
        } else {
            // 체크 해제된 경우 리스트에서 해당 데이터 제거
            selectedData = selectedData.filter(function(data) {
                return data.order_id !== rowData.order_id;
            });
        }
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

    window.openPopup = function(popupId) {
        $("#" + popupId).fadeIn();
    }

    window.closePopup = function(popupId) {
        $("#" + popupId).fadeOut();
    }

    $("#openRegistrationPopup").click(function () {
        openPopup("registrationPopup");
    });

    $("#openConfirmationPopup").click(function () {
        closePopup("registrationPopup");

        if (selectedData.length > 0) {
            var selectedItemsText = selectedData.map(function (data) {
                return 'Order ID: ' + data.order_id + ', Product Name: ' + data.product_name;
            }).join('\n');

            alert('Selected Items:\n' + selectedItemsText);

            if (!$.fn.dataTable.isDataTable('#example2')) {
                $('#example2').DataTable({
                    paging: false,
                    lengthChange: false,
                    searching: false,
                    select: false,
                    info: false,
                    data: selectedData, // 선택된 데이터로 초기화
                    columns: [
                        {data: 'order_id'},
                        {data: 'product_name'},
                        {data: 'production_quantity'},
                    ]
                });

                selectedData.forEach(function (data) {
                    var productName = data.product_name;
                    var quantity = parseInt(data.production_quantity);

                    if (productName in aggregatedQuantities) {
                        aggregatedQuantities[productName] += quantity;
                    } else {
                        aggregatedQuantities[productName] = quantity;
                    }
                });

                console.log('제품명별 production_quantity 합산:');
                console.log(aggregatedQuantities);

                if (!table3) {
                    table3 = $('#example3').DataTable({
                        paging: false,
                        lengthChange: false,
                        searching: false,
                        select: false,
                        info: false,
                        data: Object.keys(aggregatedQuantities).map(function (productName) {
                            return {
                                product_name: productName,
                                production_quantity: aggregatedQuantities[productName]
                            };
                        }),
                        columns: [
                            {data: 'product_name'},
                            {data: 'production_quantity'}
                        ]
                    });
                } else {
                    table3.clear().rows.add(Object.keys(aggregatedQuantities).map(function (productName) {
                        return {
                            product_name: productName,
                            production_quantity: aggregatedQuantities[productName]
                        };
                    })).draw();
                }

                if ('양배추즙' in aggregatedQuantities) {
                    var cabbageJuice = aggregatedQuantities['양배추즙'];
                    newObject['양배추'] += 4 * cabbageJuice;
                    newObject['벌꿀'] += Math.round(0.15 * cabbageJuice); // 벌꿀 값은 반올림하여 정수로 표현
                }
                if ('흑마늘즙' in aggregatedQuantities) {
                    var blackGarlicJuice = aggregatedQuantities['흑마늘즙'];
                    newObject['흑마늘'] += 4 * blackGarlicJuice;
                    newObject['벌꿀'] += Math.round(0.15 * blackGarlicJuice); // 벌꿀 값은 반올림하여 정수로 표현
                }
                if ('석류젤리' in aggregatedQuantities) {
                    var pomegranateJelly = aggregatedQuantities['석류젤리'];
                    newObject['석류농축액'] += Math.round(125 * pomegranateJelly); // 석류농축액 값은 반올림하여 정수로 표현
                    newObject['콜라겐'] += Math.round(0.05 * pomegranateJelly); // 콜라겐 값은 반올림하여 정수로 표현
                }
                if ('매실젤리' in aggregatedQuantities) {
                    var plumJelly = aggregatedQuantities['매실젤리'];
                    newObject['매실농축액'] += Math.round(125 * plumJelly); // 매실농축액 값은 반올림하여 정수로 표현
                    newObject['콜라겐'] += Math.round(0.05 * plumJelly); // 콜라겐 값은 반올림하여 정수로 표현
                }

                console.log(newObject);

                const table4 = $('#example4').DataTable({
                    paging: false,
                    lengthChange: false,
                    searching: false,
                    select: false,
                    info: false,
                    data: [newObject],
                    columns: [
                        {data: '양배추'},
                        {data: '흑마늘'},
                        {
                            data: '벌꿀',
                            render: function (data, type, row) {
                                if (type === 'display' || type === 'filter') {
                                    return Math.round(data);
                                }
                                return data;
                            },
                        },
                        {data: '석류농축액'},
                        {data: '매실농축액'},
                        {
                            data: '콜라겐',
                            render: function (data, type, row) {
                                if (type === 'display' || type === 'filter') {
                                    return Math.round(data);
                                }
                                return data;
                            },
                        }
                    ]
                });

            } else {
                const table = $('#example2').DataTable();
                table3.clear().rows.add(selectedData).draw();
            }

            openPopup("confirmationPopup");

            newObject = {
                '벌꿀': 0,
                '양배추': 0,
                '흑마늘': 0,
                '석류농축액': 0,
                '매실농축액': 0,
                '콜라겐': 0
            };
        } else {
            alert('No data selected.');
            openPopup("registrationPopup");
        }
    });

    $("#regist").click(function () {
        var selectedOrderIds = [];
        var table2Data = $('#example2').DataTable().rows().data();

        table2Data.each(function (value, index) {
            selectedOrderIds.push(value.order_id);
        });

        $.ajax({
            url: '/api/updateOrderDeletable',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({orderIds: selectedOrderIds}),
            success: function (response) {
                console.log('Deletable 속성이 업데이트되었습니다.');
                $('#example1').DataTable().ajax.reload();
            },
            error: function (xhr, status, error) {
                console.error('업데이트 중 오류 발생:', error);
            }
        });

        if (selectedData.length > 0) {
            selectedData.forEach(function (data) {
                var orderQuantity = parseInt(data.production_quantity);

                if ('양배추즙' in aggregatedQuantities) {
                    var cabbageJuice = aggregatedQuantities['양배추즙'];
                    savePurchaseMaterial(data, '양배추즙', 4 * cabbageJuice, orderQuantity, data.order_id);
                    savePurchaseMaterial(data, '벌꿀', Math.round(0.15 * cabbageJuice), orderQuantity, data.order_id);
                }
                if ('흑마늘즙' in aggregatedQuantities) {
                    var blackGarlicJuice = aggregatedQuantities['흑마늘즙'];
                    savePurchaseMaterial(data, '흑마늘즙', 4 * blackGarlicJuice, orderQuantity, data.order_id);
                    savePurchaseMaterial(data, '벌꿀', Math.round(0.15 * blackGarlicJuice), orderQuantity, data.order_id);
                }
                if ('석류젤리' in aggregatedQuantities) {
                    var pomegranateJelly = aggregatedQuantities['석류젤리'];
                    savePurchaseMaterial(data, '석류젤리', Math.round(125 * pomegranateJelly), orderQuantity, data.order_id);
                    savePurchaseMaterial(data, '콜라겐', Math.round(0.05 * pomegranateJelly), orderQuantity, data.order_id);
                }
                if ('매실젤리' in aggregatedQuantities) {
                    var plumJelly = aggregatedQuantities['매실젤리'];
                    savePurchaseMaterial(data, '매실젤리', Math.round(125 * plumJelly), orderQuantity, data.order_id);
                    savePurchaseMaterial(data, '콜라겐', Math.round(0.05 * plumJelly), orderQuantity, data.order_id);
                }
            });
        }

        function savePurchaseMaterial(orderData, rawMaterial, quantity, orderQuantity, orderId) {
            var purchaseMaterial = {
                rawMaterial: rawMaterial,
                orderQuantity: quantity,
                purchaseDate: new Date(),
                deliveryCompletionDate: null,
                orderId: {
                    orderId: orderId
                }
            };

            console.log(purchaseMaterial);

            $.ajax({
                url: '/api/savePurchaseMaterial',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(purchaseMaterial),
                success: function (response) {
                    console.log('원자재 데이터가 성공적으로 저장되었습니다.');
                },
                error: function (xhr, status, error) {
                    console.error('원자재 데이터 저장 중 오류 발생:', error);
                }
            });
        }
    });
});