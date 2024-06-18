package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Production_Planning_Dto {

    private Integer plan_id;
    private Integer equipment_id;
    private Date estimated_start_date;
    private Date estimated_end_date;
    private Date start_date;
    private Date end_date;

}
