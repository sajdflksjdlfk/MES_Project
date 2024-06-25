package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Equipment5_plan_date_Dto {
    // 살균기1 계획
    private int equipmentId;
    private LocalDateTime estimatedStartDate;
    private LocalDateTime estimatedEndDate;
    private int input;
    private int output;
}
