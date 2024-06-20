package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Finish_product;
import com.codehows.zegozero.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinishProductRepository extends JpaRepository<Finish_product, Integer> {

    @Query("SELECT o FROM Finish_product o WHERE o.received_quantity IS NULL")
    List<Finish_product> findByReceivedQuantityIsNull();

    @Query("SELECT o FROM Finish_product o WHERE o.shipped_quantity IS NULL")
    List<Finish_product> findByShippedQuantityIsNull();

}
