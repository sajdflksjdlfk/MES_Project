package com.codehows.zegozero.controller;

import com.codehows.zegozero.service.PlanEquipmentService;
import com.codehows.zegozero.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class Plan_equipment_api_controller {

    private final PlanEquipmentService planEquipmentService;

    // 설비 3 및 설비 4의 마지막 예상 출고 날짜 비교
    @GetMapping("/earliestEndDate")
    public ResponseEntity<Object> getEquipmentDetails(
            @RequestParam int equipmentId3,
            @RequestParam int equipmentId4,
            @RequestParam int input) {

        Object equipmentDetails = planEquipmentService.findEarliestEndDateForEquipments(equipmentId3, equipmentId4, input);

        if (equipmentDetails != null) {
            return ResponseEntity.ok(equipmentDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
