package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Plan_equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanEquipmentRepository  extends JpaRepository<Plan_equipment, Integer> {
}
