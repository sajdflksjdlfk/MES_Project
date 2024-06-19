package com.codehows.zegozero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    private String product_name;

    private int quantity;

    private int used_inventory;

    private int production_quantity;

    private Date order_date;

    private Date expected_shipping_date;

    private String customer_name;

    private String delivery_address;

    private Date shipping_date;

    private Boolean deletable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan")
    private Plans plan;

    //원자재 발주 여부 추가.
    private boolean ordered;

}
