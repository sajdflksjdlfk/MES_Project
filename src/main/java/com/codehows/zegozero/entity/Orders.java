package com.codehows.zegozero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    private String product_name;

    private int quantity;

    private int used_inventory;

    private Date order_date;

    private Date expected_shipping_date;

    private String customer_name;

    private String delivery_address;

    private Date shipping_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan")
    private Plans plan;

}
