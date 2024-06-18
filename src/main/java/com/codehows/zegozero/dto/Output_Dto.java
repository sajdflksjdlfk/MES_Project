package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Output_Dto {

    private int received_quantity;
    private Date received_date;

}
