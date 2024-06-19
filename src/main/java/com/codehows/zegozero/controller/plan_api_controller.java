package com.codehows.zegozero.controller;

import com.codehows.zegozero.entity.Plans;
import com.codehows.zegozero.repository.PlansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class plan_api_controller {

    private final PlansRepository plansRepository;

    @GetMapping("/findLatestPlanByProductName")
    public Plans findLatestPlanByProductName(@RequestParam String productName){
        return plansRepository.findLatestPlanByProductName(productName);
    }


}
