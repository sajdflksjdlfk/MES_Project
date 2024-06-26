package com.codehows.zegozero.service;

import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.entity.Plans;
import com.codehows.zegozero.repository.PlansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlansRepository plansRepository;
    private final List<Plans> temporaryPlans = new ArrayList<>(); // 변경: Plans 엔티티의 리스트로 변경

    int latestPlanQuantity = 0; // 기존 마지막 계획에 잡혀있는 수량
    int totalProductionQuantity = 0; // 실제작수량
    int numberOfPlans = 0; // 새로 만들어야하는 생산계획 수
    int[] result = new int[2];

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

                for (int i = 0; i < numberOfPlans; i++) {
                    Plans plan = new Plans();
                    plan.setProduct_name(productName);
                    if (i == numberOfPlans - 1) {
                        plan.setPlanned_quantity(totalProductionQuantity);
                    } else {
                        plan.setPlanned_quantity(333);
                    }
                    plan.setStatus("planned");
                    temporaryPlans.add(plan);
                }

                result[0] = numberOfPlans;
                result[1] = totalProductionQuantity;
            } else {
                numberOfPlans = 0;
                result[0] = numberOfPlans;
                result[1] = totalProductionQuantity;
            }
        } else {
            latestPlanQuantity = 0;
            totalProductionQuantity = productionQuantity;
            numberOfPlans = 1;
            // 새로 만들어야할 계획 수
            while (totalProductionQuantity > 333) {
                totalProductionQuantity -= 333;
                numberOfPlans++;
            }

            for (int i = 0; i < numberOfPlans; i++) {
                Plans plan = new Plans();
                plan.setProduct_name(productName);
                if (i == numberOfPlans - 1) {
                    plan.setPlanned_quantity(totalProductionQuantity);
                } else {
                    plan.setPlanned_quantity(333);
                }
                plan.setStatus("planned");
                temporaryPlans.add(plan);
            }

            result[0] = numberOfPlans;
            result[1] = totalProductionQuantity;
        }

        return result;
    }

    // 최종적으로 엔티티를 데이터베이스에 저장
    @Transactional
    public void saveAllPlans() {
        plansRepository.saveAll(temporaryPlans);
        temporaryPlans.clear(); // 저장 후 임시 리스트 초기화
    }

    // 임시 계획 리스트를 초기화하는 메서드 추가
    public void clearTemporaryPlans() {
        temporaryPlans.clear();
    public Plans findById(Integer id) {
        return plansRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
}
