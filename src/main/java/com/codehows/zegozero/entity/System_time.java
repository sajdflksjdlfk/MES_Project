package com.codehows.zegozero.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "system_time")
@NoArgsConstructor
@AllArgsConstructor
public class System_time {

    @Id
    @Column(name = "time")
    private Date time;

}
