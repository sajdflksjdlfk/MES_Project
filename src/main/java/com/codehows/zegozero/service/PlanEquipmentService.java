package com.codehows.zegozero.service;

import com.codehows.zegozero.dto.*;
import com.codehows.zegozero.entity.Plan_equipment;
import com.codehows.zegozero.repository.PlanEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

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

    // 세척 계획을 생성하는 메서드
    public Equipment2_plan_date_Dto createCleaningPlan(int id2Input, int id2Output, int cleaningTimeMinutes, LocalDateTime cleaningStartDateTime) {
        // id2 설비를 찾는 메서드
        List<Plan_equipment> existingPlans = planEquipmentRepository.findAllByEquipmentEquipmentId(2);

        // 겹치지 않도록 시작 시간을 조정
        LocalDateTime adjustedStartTime = id2StartTime(cleaningStartDateTime, cleaningTimeMinutes, existingPlans);

        // 종료 시간 계산
        LocalDateTime endTime = adjustedStartTime.plusMinutes(cleaningTimeMinutes);

        // DTO 반환
        return new Equipment2_plan_date_Dto(2, adjustedStartTime, endTime, id2Input, id2Output);
    }

    // 여과기 계획 생성 메서드
    public Equipment9_plan_date_Dto createEquipment9Plan(LocalDateTime id34EndDate, int id9Input) {
        // 설비9 계획 시작 시간 설정 (id34EndDate를 기준으로 시작)
        LocalDateTime proposedStartTime = id34EndDate;

        // 서비스에서 가져온 설비9 계획들
        List<Plan_equipment> existingPlans = planEquipmentRepository.findAllByEquipmentEquipmentId(9);

        // 겹치지 않도록 시작 시간을 조정
        LocalDateTime id9StartTime = id9StartTime(proposedStartTime, existingPlans);

        // DTO에 저장
        Equipment9_plan_date_Dto equipmentDto = new Equipment9_plan_date_Dto(
                9,
                id9StartTime,
                id9StartTime.plusHours(4), // 조정된 시작 시간에 4시간을 더하여 종료 시간 설정
                id9Input,
                (int) Math.floor(id9Input * 0.5) // 여과기 산출량은 투입량의 50%
        );

        // DTO 반환
        return equipmentDto;
    }

    // 살균기 설비 계획 수립
    public Object id56Plan(LocalDateTime id56EndDate, int id56Input) {
        // 설비 5와 설비 6의 기존 계획들을 가져옴
        List<Plan_equipment> existingPlans5 = planEquipmentRepository.findAllByEquipmentEquipmentId(5);
        List<Plan_equipment> existingPlans6 = planEquipmentRepository.findAllByEquipmentEquipmentId(6);

        // 설비 5와 6의 계획을 겹치지 않도록 조정하고 선택
        LocalDateTime adjustedStartTime5 = id56StartTime(id56EndDate, existingPlans5);
        LocalDateTime adjustedStartTime6 = id56StartTime(id56EndDate, existingPlans6);

        LocalDateTime selectedStartTime;
        int selectedEquipmentId;

        // 설비5와 6의 계획을 검토하여 가장 빠른 가능 시간을 선택
        if (adjustedStartTime5.isBefore(adjustedStartTime6)) {
            selectedStartTime = adjustedStartTime5;
            selectedEquipmentId = 5;
        } else {
            selectedStartTime = adjustedStartTime6;
            selectedEquipmentId = 6;
        }

        if (selectedEquipmentId == 5) {
            Equipment5_plan_date_Dto equipmentDto = new Equipment5_plan_date_Dto(
                    selectedEquipmentId,
                    selectedStartTime,
                    selectedStartTime.plusHours(2), // 조정된 시작 시간에 2시간을 더하여 종료 시간 설정
                    id56Input,
                    id56Input
            );
            return equipmentDto;
        } else {
            Equipment6_plan_date_Dto equipmentDto = new Equipment6_plan_date_Dto(
                    selectedEquipmentId,
                    selectedStartTime,
                    selectedStartTime.plusHours(2), // 조정된 시작 시간에 2시간을 더하여 종료 시간 설정
                    id56Input,
                    id56Input
            );
            return equipmentDto;
        }
    }

    // 세척 시작 시간을 조정하는 메서드
    private LocalDateTime id2StartTime(LocalDateTime proposedStartTime, int cleaningTimeMinutes, List<Plan_equipment> existingPlans) {
        for (Plan_equipment plan : existingPlans) {
            LocalDateTime existingStart = plan.getEstimated_start_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();
            LocalDateTime existingEnd = plan.getEstimated_end_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();

            // 제안된 시작 시간이 기존 계획의 끝 이전에 있고, 제안된 끝 시간이 기존 계획의 시작 이후인 경우
            if (proposedStartTime.isBefore(existingEnd) && proposedStartTime.plusMinutes(cleaningTimeMinutes).isAfter(existingStart)) {
                // 제안된 시작 시간을 기존 계획 시작 시간 이전으로 조정
                proposedStartTime = existingStart.minusMinutes(cleaningTimeMinutes);
            }
        }
        return proposedStartTime;
    }

    // 여과기 시작 시간을 조정하는 메서드
    private LocalDateTime id9StartTime(LocalDateTime proposedStartTime, List<Plan_equipment> existingPlans) {
        for (Plan_equipment plan : existingPlans) {
            LocalDateTime existingStart = plan.getEstimated_start_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();
            LocalDateTime existingEnd = plan.getEstimated_end_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime()
                    .plusHours(1); // 기존 계획 종료 시간에 1시간 추가

            // 제안된 시작 시간이 기존 계획의 끝 이전에 있고, 제안된 끝 시간이 기존 계획의 시작 이후인 경우
            if (proposedStartTime.isBefore(existingEnd) && proposedStartTime.plusHours(5).isAfter(existingStart)) {
                // 제안된 시작 시간을 기존 계획 시작 시간 이후로 조정
                proposedStartTime = existingEnd;
            }
        }
        return proposedStartTime;
    }

    

    // 살균기 시간을 조정하는 메서드
    private LocalDateTime id56StartTime(LocalDateTime proposedStartTime, List<Plan_equipment> existingPlans) {
        LocalDateTime adjustedTime = proposedStartTime;

        for (Plan_equipment plan : existingPlans) {
            LocalDateTime existingStart = plan.getEstimated_start_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();
            LocalDateTime existingEnd = plan.getEstimated_end_date().toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime().plusHours(1); // 기존 계획 종료 시간에 1시간 추가

            if (adjustedTime.isBefore(existingEnd) && adjustedTime.plusHours(3).isAfter(existingStart)) {
                adjustedTime = existingEnd;
            }
        }

        return adjustedTime;
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
