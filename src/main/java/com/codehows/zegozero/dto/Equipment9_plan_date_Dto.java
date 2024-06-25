package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Equipment9_plan_date_Dto {
    // 여과기 계획
    private int equipmentId;
    private LocalDateTime estimatedStartDate;
    private LocalDateTime estimatedEndDate;
    private int input;
    private int output;
}
