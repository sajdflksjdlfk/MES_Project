package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlansRepository extends JpaRepository<Plans, Integer> {

    @Query("SELECT p FROM Plans p " +
            "WHERE p.product_name = :productName " +
            "AND p.status = 'planned' " + // 'start' 상태의 계획을 찾습니다. 필요에 따라 변경 가능합니다.
            "ORDER BY p.plan_id DESC") // plan_id를 기준으로 내림차순 정렬하여 최신 계획을 가져옵니다.
    Plans findLatestPlanByProductName(@Param("productName") String productName);

}
