package com.codehows.zegozero.controller;

import com.codehows.zegozero.entity.Plans;
import com.codehows.zegozero.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class plan_api_controller {

    private final PlanService planService;

    // 생산계획 개수와 제작수량 계산
    @GetMapping("/calculateProductionQuantity")
    public ResponseEntity<int[]> calculateProductionQuantity(@RequestParam String productName, @RequestParam int productionQuantity) {
        int[] result = planService.calculateProductionQuantity(productName, productionQuantity);
        return ResponseEntity.ok(result);
    }

}
