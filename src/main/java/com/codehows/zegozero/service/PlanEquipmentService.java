package com.codehows.zegozero.service;

import com.codehows.zegozero.dto.Equipment3_plan_date_Dto;
import com.codehows.zegozero.dto.Equipment4_plan_date_Dto;
import com.codehows.zegozero.entity.Plan_equipment;
import com.codehows.zegozero.repository.PlanEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanEquipmentService {

    private final PlanEquipmentRepository planEquipmentRepository;

    // 설비3 및 설비4의 마지막 예상 출고 날짜 비교
    @Transactional(readOnly = true)
    public Object findEarliestEndDateForEquipments(int equipmentId3, int equipmentId4, int input) {
        Optional<Plan_equipment> equipment1Optional = planEquipmentRepository.findLatestPlanEquipmentByEquipmentId(equipmentId3);
        Optional<Plan_equipment> equipment2Optional = planEquipmentRepository.findLatestPlanEquipmentByEquipmentId(equipmentId4);

        int output = input / 5;

        // 현재 한국 시간
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 설비 3과 설비 4 모두 데이터가 없는 경우
        if (equipment1Optional.isEmpty() && equipment2Optional.isEmpty()) {
            // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
            LocalDateTime currentDatePlus3Days = now.plusDays(3);
            int equipmentId = 3;
            return new Equipment3_plan_date_Dto(
                    equipmentId,
                    roundUpToNearest10Minutes(currentDatePlus3Days),
                    roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                    input,
                    output
            );
        }

        // 설비 3만 데이터가 있는 경우
        if (equipment1Optional.isPresent() && equipment2Optional.isEmpty()) {
            Date endDate3 = equipment1Optional.get().getEstimated_end_date();
            // 현재 시간에 1일과 23시간을 더한 날짜
            LocalDateTime currentDatePlus1Day23Hours = now.plusDays(1).plusHours(23);

            // 현재 시간 + 1일과 23시간보다 빠른 경우
            if (endDate3 != null && endDate3.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant()))) {
                // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
                LocalDateTime currentDatePlus3Days = now.plusDays(3);
                int equipmentId = 3;
                return new Equipment3_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            } else {
                // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
                LocalDateTime currentDatePlus3Days = now.plusDays(3);
                int equipmentId = 4;
                return new Equipment4_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            }
        }

        // 설비 4만 데이터가 있는 경우
        if (equipment2Optional.isPresent() && equipment1Optional.isEmpty()) {
            Date endDate4 = equipment2Optional.get().getEstimated_end_date();
            // 현재 시간에 1일과 23시간을 더한 날짜
            LocalDateTime currentDatePlus1Day23Hours = now.plusDays(1).plusHours(23);

            // 현재 시간 + 1일과 23시간보다 빠른 경우
            if (endDate4 != null && endDate4.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant()))) {
                // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
                LocalDateTime currentDatePlus3Days = now.plusDays(3);
                int equipmentId = 3;
                return new Equipment3_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            } else {
                // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
                LocalDateTime currentDatePlus3Days = now.plusDays(3);
                int equipmentId = 4;
                return new Equipment4_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            }
        }

        // 설비 3과 설비 4 모두 데이터가 있는 경우
        Date endDate3 = equipment1Optional.get().getEstimated_end_date();
        Date endDate4 = equipment2Optional.get().getEstimated_end_date();

        // 현재 시간에 1일과 23시간을 더한 날짜
        LocalDateTime currentDatePlus1Day23Hours = now.plusDays(1).plusHours(23);

        // 현재 시간 + 1일과 23시간보다 빠른 경우
        if (endDate3 != null && endDate3.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant()))
                || (endDate4 != null && endDate4.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant())))) {
            // 현재 시간에 3일을 더한 날짜를 반환하되, 10분 단위로 올림 처리
            LocalDateTime currentDatePlus3Days = now.plusDays(3);
            if (endDate3 != null && endDate3.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant()))) {
                int equipmentId = 3;
                return new Equipment3_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            } else if (endDate4 != null && endDate4.before(Date.from(currentDatePlus1Day23Hours.atZone(ZoneId.systemDefault()).toInstant()))) {
                int equipmentId = 4;
                return new Equipment4_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(currentDatePlus3Days),
                        roundUpToNearest10Minutes(currentDatePlus3Days.plusDays(1)),
                        input,
                        output
                );
            }
        } else {
            // 설비 예상 종료 시간 중에서 더 빠른 것 반환 후 +1일 1시간 추가
            Date earliestEndDate = endDate3.before(endDate4) ? endDate3 : endDate4;
            LocalDateTime earliestEndDateLocalDateTime = LocalDateTime.ofInstant(earliestEndDate.toInstant(), ZoneId.systemDefault());
            LocalDateTime modifiedEndDate = earliestEndDateLocalDateTime.plusDays(1).plusHours(1);
            LocalDateTime modifiedEndDateAdd1Hour = modifiedEndDate.plusDays(1);

            if (earliestEndDate.equals(endDate3)) {
                int equipmentId = 3;
                return new Equipment3_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(modifiedEndDate),
                        roundUpToNearest10Minutes(modifiedEndDateAdd1Hour),
                        input,
                        output
                );
            } else {
                int equipmentId = 4;
                return new Equipment4_plan_date_Dto(
                        equipmentId,
                        roundUpToNearest10Minutes(modifiedEndDate),
                        roundUpToNearest10Minutes(modifiedEndDateAdd1Hour),
                        input,
                        output
                );
            }
        }

        // 예외 상황 처리 - 이 코드는 실제로는 실행되지 않아야 하지만, 컴파일 오류를 방지하기 위해 넣었습니다.
        return null;
    }

    // 주어진 날짜를 10분 단위로 올림하여 반환하는 메서드
    private LocalDateTime roundUpToNearest10Minutes(LocalDateTime dateTime) {
        int minute = dateTime.getMinute();
        int remainder = minute % 10;
        if (remainder != 0) {
            dateTime = dateTime.plusMinutes(10 - remainder);
        }
        return dateTime.withSecond(0).withNano(0);
    }

}
