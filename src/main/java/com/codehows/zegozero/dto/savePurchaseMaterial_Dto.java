package com.codehows.zegozero.dto;

import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.entity.Purchase_matarial;
import jakarta.persistence.*;

import java.util.Date;

public class savePurchaseMaterial_Dto {

    private int purchase_matarial_id;

    private String raw_material;

    private int order_quantity;

    private Date purchase_date;

    private Date delivery_completion_date;

    private String delivery_status = "배송중";

    private Orders order_id;


    public Purchase_matarial toEntity() {
        return Purchase_matarial.builder()
                .purchase_matarial_id(purchase_matarial_id)
                .raw_material(raw_material)
                .order_quantity(order_quantity)
                .purchase_date(purchase_date)
                .delivery_status(delivery_status)
                .delivery_completion_date(delivery_completion_date)
                .order_id(order_id)
                .build();
    }
}
