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
public class Finished_product_management_Dto {
    private Integer order_id;
    private String product_name;
    private Integer received_quantity;
    private Date received_date;
    private Integer shipped_quantity;
    private Date shipped_date;
}
