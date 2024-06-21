package com.codehows.zegozero.controller;

import com.codehows.zegozero.dto.Equipment2_plan_date_Dto;
import com.codehows.zegozero.dto.Equipment9_plan_date_Dto;
import com.codehows.zegozero.service.PlanEquipmentService;
import com.codehows.zegozero.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    // 설비2 계획 잡기
    @GetMapping("/id2Plan")
    public ResponseEntity<Equipment2_plan_date_Dto> id2Plan(
            @RequestParam int id2Input,
            @RequestParam int id2Output,
            @RequestParam int cleaningTimeMinutes,
            @RequestParam String cleaningStartDateTime){

        LocalDateTime startDateTime = LocalDateTime.parse(cleaningStartDateTime, DateTimeFormatter.ISO_DATE_TIME);
        Equipment2_plan_date_Dto equipment2Plan = planEquipmentService.createCleaningPlan(id2Input, id2Output, cleaningTimeMinutes, startDateTime);

        return ResponseEntity.ok(equipment2Plan);

    }

    // 설비9 계획 잡기
    @GetMapping("/id9Plan")
    public ResponseEntity<Equipment9_plan_date_Dto> id9Plan(
            @RequestParam LocalDateTime id34EndDate,
            @RequestParam int id9Input) {

        Equipment9_plan_date_Dto equipment9Plan = planEquipmentService.createEquipment9Plan(id34EndDate, id9Input);

        return ResponseEntity.ok(equipment9Plan);
    }

    // 설비 56 계획 잡기
    @GetMapping("/id56Plan")
    public ResponseEntity<Object> id56Plan(
            @RequestParam String id56EndDate,
            @RequestParam int id56Input) {

        LocalDateTime startDateTime = LocalDateTime.parse(id56EndDate, DateTimeFormatter.ISO_DATE_TIME);
        Object equipment56Plan = planEquipmentService.id56Plan(startDateTime, id56Input);

        return ResponseEntity.ok(equipment56Plan);
    }
}
