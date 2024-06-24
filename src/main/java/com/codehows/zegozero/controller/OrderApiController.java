package com.codehows.zegozero.controller;

import com.codehows.zegozero.dto.OrderUpdateRequest_Dto;
import com.codehows.zegozero.dto.Order_Dto;

import com.codehows.zegozero.dto.savePurchaseMaterial_Dto;
import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.entity.Purchase_matarial;
import com.codehows.zegozero.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> registApiOrder(@Valid @RequestBody Order_Dto Orderdata, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        if (Orderdata.getProduction_quantity() < 0) {
            return ResponseEntity.badRequest().body("Production quantity cannot be negative");
        }

        orderService.save(Orderdata);

        // 원하는 작업 후 데이터를 담은 객체를 반환
        return ResponseEntity.ok().body("Order saved successfully");
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


    @GetMapping("/orderPlan")
    public Map<String, Object> orderPlan() throws IOException {
        Boolean deletable = true;
        Map<String, Object> orderPlan = new HashMap<String, Object>();

        List<Order_Dto> orderPlan1 = orderService.findByDeletable(deletable)
                .stream()
                .map(a ->new Order_Dto(a))
                .collect(Collectors.toList());

        System.out.println("123");

        orderPlan.put("data",orderPlan1);

        System.out.println("12333");
        // 원하는 작업 후 데이터를 담은 객체를 반환
        return orderPlan;
    }


    @PostMapping("/updateOrderDeletable")
    public ResponseEntity<String> updateOrderDeletable(@RequestBody OrderUpdateRequest_Dto request) {
        try {
            orderService.updateOrderDeletable(request.getOrderIds());
            return ResponseEntity.ok("Deletable 속성이 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류 발생");
        }

    }


    @GetMapping("/delivered")
    public Map<String, Object> delivered() throws IOException {

        Boolean deletable = false;
        Map<String, Object> delivered = new HashMap<String, Object>();

        List<Order_Dto> delivered1 = orderService.findByDeletable(deletable)
                .stream()
                .map(a ->new Order_Dto(a))
                .collect(Collectors.toList());

        delivered.put("data",delivered1);

        // 원하는 작업 후 데이터를 담은 객체를 반환

        return delivered;
    }

    @PostMapping("savePurchaseMaterial")
    public ResponseEntity<?> savePurchaseMaterial(@RequestBody savePurchaseMaterial_Dto save){

        Purchase_matarial savePurchaseMaterial =orderService.savePurchaseMaterial(save);
        return ResponseEntity.ok()
                .body(savePurchaseMaterial);
    }





}
