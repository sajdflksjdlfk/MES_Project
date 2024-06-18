package com.codehows.zegozero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "finish_product")
@NoArgsConstructor
@AllArgsConstructor
public class Finish_product {

    @Id
    @Column(name = "finish_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int finish_product_id;

    private String product_name;

    private int received_quantity;

    private Date received_date;

    private int shipped_quantity;

    private Date shipped_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders order_id;

}
