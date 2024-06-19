package com.codehows.zegozero.service;

import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class orderService {

    private final OrdersRepository ordersRepository;

    //"원자재 발주 완료 여부를 확인"해 원자재 "발주 계획" 작성에 사용하는 매서드
    public List<Orders> findByOrdered(Boolean ordered){
        return ordersRepository.findByOrdered(ordered);
    }


}
