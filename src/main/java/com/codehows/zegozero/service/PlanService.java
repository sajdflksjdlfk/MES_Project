package com.codehows.zegozero.service;

import com.codehows.zegozero.repository.PlansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlansRepository plansRepository;

}
