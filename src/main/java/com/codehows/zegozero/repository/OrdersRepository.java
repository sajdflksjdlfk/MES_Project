package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByDeletable(Boolean deletable);
    List<Orders> findAllByOrderIdIn(List<Integer> orderIds);


}
