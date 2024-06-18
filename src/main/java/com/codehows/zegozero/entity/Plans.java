package com.codehows.zegozero.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "plans")
@NoArgsConstructor
@AllArgsConstructor
public class Plans {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plan_id;

    private String product_name;

    private int planned_quantity;

    private Date start_date;

    private Date completion_date;

    private String status = "대기중";

}
