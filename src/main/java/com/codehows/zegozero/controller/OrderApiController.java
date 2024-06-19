package com.codehows.zegozero.controller;

import com.codehows.zegozero.dto.Order_Dto;
import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> registApiOrder(@RequestBody Order_Dto Orderdata) throws IOException {

        orderService.save(Orderdata);

        // 원하는 작업 후 데이터를 담은 객체를 반환
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/progressorder")
    public Map<String, Object> progressApiOrder() throws IOException {

        Map<String, Object> Order_DtoList1 = new HashMap<String, Object>();

        List<Order_Dto> Order_DtoList = orderService.findAll().stream()
                        .map(a ->new Order_Dto(a))
                        .collect(Collectors.toList());

        Order_DtoList1.put("data",Order_DtoList);

        // 원하는 작업 후 데이터를 담은 객체를 반환
        return Order_DtoList1;
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestBody Map<String, Integer> request) {
        Integer orderId = request.get("order_id");
        if (orderId != null) {
            // orderId를 사용하여 삭제 로직을 처리
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().body("Order deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Order ID is missing");
        }
    }




}
