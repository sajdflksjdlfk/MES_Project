package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Facility_management_Dto {
    private Integer equipment_plan_id;
    private Date estimated_start_date;
    private Date estimated_end_date;
}
