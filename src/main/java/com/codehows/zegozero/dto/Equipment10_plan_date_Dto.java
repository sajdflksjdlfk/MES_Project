package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Equipment10_plan_date_Dto {
    // 충진기1,2(즙) 계획
    private int equipmentId;
    private LocalDateTime estimatedStartDate;
    private LocalDateTime estimatedEndDate;
    private int input;
    private int output;
}
