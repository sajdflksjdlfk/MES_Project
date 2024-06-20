package com.codehows.zegozero.service;

import com.codehows.zegozero.entity.Plan_equipment;
import com.codehows.zegozero.entity.Plans;
import com.codehows.zegozero.repository.PlanEquipmentRepository;
import com.codehows.zegozero.repository.PlansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanEquipmentRepository planEquipmentRepository;
    private final PlansRepository plansRepository;

    private List<Plans> temporaryPlans = new ArrayList<>(); // 임시 저장 리스트

    int latestPlanQuantity = 0; // 기존 마지막 계획에 잡혀있는 수량
    int totalProductionQuantity = 0; // 실제작수량
    int numberOfPlans = 0; // 새로 만들어야하는 생산계획 수
    int[] result = new int[2]; // 배열을 사용해 두 값을 함께 반환

    // 제품명과 제작수량을 받아서 새로운 계획을 만들지 기존 계획에 추가할지 판단
    @Transactional(readOnly = true)
    public int[] calculateProductionQuantity(String productName, int productionQuantity) {
        PageRequest pageRequest = PageRequest.of(0, 1); // 첫 번째 페이지의 하나의 항목만 가져옴
        Plans latestPlan = plansRepository.findLatestPlanByProductName(productName, pageRequest).getContent().stream().findFirst().orElse(null);

        if (latestPlan != null) {
            latestPlanQuantity = latestPlan.getPlanned_quantity();
            totalProductionQuantity = latestPlanQuantity + productionQuantity;

            // 기존 생산계획에 추가할 수 있는지 판단
            if (totalProductionQuantity > 333) {
                totalProductionQuantity = productionQuantity;
                numberOfPlans = 1;
                // 새로 만들어야할 계획 수
                while (totalProductionQuantity > 333) {
                    totalProductionQuantity -= 333;
                    numberOfPlans++;
                }
                result[0] = numberOfPlans;
                result[1] = totalProductionQuantity;
            } else {
                numberOfPlans = 0;
                // 기존 계획에 추가
                latestPlan.setPlanned_quantity(totalProductionQuantity); // 기존 계획의 수량 업데이트
                temporaryPlans.add(latestPlan); // 업데이트된 계획을 임시 리스트에 추가
                result[0] = numberOfPlans;
                result[1] = totalProductionQuantity;
            }
        } else {
            latestPlanQuantity = 0;
            totalProductionQuantity = productionQuantity;
            numberOfPlans = 0;
            // 새로 만들어야할 계획 수
            while (totalProductionQuantity > 333) {
                totalProductionQuantity -= 333;
                numberOfPlans++;
            }
            result[0] = numberOfPlans;
            result[1] = totalProductionQuantity;
        }

        return result;
    }

    // 최종적으로 계획들을 데이터베이스에 저장
    @Transactional
    public void saveAllPlans() {
        plansRepository.saveAll(temporaryPlans);
        temporaryPlans.clear(); // 저장 후 임시 리스트 초기화
    }
}
