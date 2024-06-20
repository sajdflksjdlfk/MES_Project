package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Plan_equipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface PlanEquipmentRepository extends JpaRepository<Plan_equipment, Integer> {

    @Query("SELECT pe " +
            "FROM Plan_equipment pe " +
            "WHERE pe.equipment.equipment_id = :equipmentId " +
            "AND pe.estimated_end_date = (" +
            "SELECT MAX(p.estimated_end_date) " +
            "FROM Plan_equipment p " +
            "WHERE p.equipment.equipment_id = :equipmentId" +
            ")")
    Optional<Plan_equipment> findLatestPlanEquipmentByEquipmentId(@Param("equipmentId") int equipmentId);


}
