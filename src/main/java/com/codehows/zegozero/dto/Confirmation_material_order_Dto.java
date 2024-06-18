package com.codehows.zegozero.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Confirmation_material_order_Dto {
    private Integer order_id;
    private String raw_material;
    private Integer production_quantity;
}
