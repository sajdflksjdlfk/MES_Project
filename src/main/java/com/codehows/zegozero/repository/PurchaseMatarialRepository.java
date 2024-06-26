package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Purchase_matarial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseMatarialRepository  extends JpaRepository<Purchase_matarial, Integer> {
}
