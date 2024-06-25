package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Equipment2_plan_date_Dto {
    // 세척 계획
    private int equipmentId;
    private LocalDateTime estimatedStartDate;
    private LocalDateTime estimatedEndDate;
    private int input;
    private int output;
}
