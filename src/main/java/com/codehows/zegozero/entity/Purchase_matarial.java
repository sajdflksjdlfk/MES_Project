package com.codehows.zegozero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "purchase_matarial")
@NoArgsConstructor
@AllArgsConstructor
public class Purchase_matarial {

    @Id
    @Column(name = "purchase_matarial_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchase_matarial_id;

    private String raw_material;

    private int order_quantity;

    private Date purchase_date;

    private Date delivery_completion_date;

    private String delivery_status = "배송중";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders order_id;

}
