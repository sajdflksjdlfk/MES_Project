package com.codehows.zegozero.repository;

import com.codehows.zegozero.entity.System_time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemTimeRepository  extends JpaRepository<System_time, Long> {

    Optional<System_time> findFirstByOrderById();

}
