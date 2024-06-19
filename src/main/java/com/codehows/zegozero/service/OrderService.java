package com.codehows.zegozero.service;

import com.codehows.zegozero.dto.Order_Dto;
import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    public void save(Order_Dto Orderdata) {

        Orders orders = new Orders();
        orders.setProduct_name(Orderdata.getProduct_name());
        orders.setQuantity(Orderdata.getQuantity());
        orders.setUsed_inventory(Orderdata.getUsed_inventory());
        orders.setProduction_quantity(Orderdata.getProduction_quantity());
        orders.setOrder_date(new Date());
        orders.setExpected_shipping_date(Orderdata.getExpected_shipping_date());
        orders.setCustomer_name(Orderdata.getCustomer_name());
        orders.setDelivery_address(Orderdata.getDelivery_address());
        orders.setDeletable(true);

        ordersRepository.save(orders);
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    // shipping_date가 null인 Orders를 찾는 메서드
    public List<Orders> findAllByShippingDateIsNull() {
        return ordersRepository.findAllByShippingDateIsNull();
    }

    // shipping_date가 null이 아닌 Orders를 찾는 메서드
    public List<Orders> findAllByShippingDateIsNotNull() {
        return ordersRepository.findAllByShippingDateIsNotNull();
    }

    //게시글 삭제
    @Transactional
    public void deleteOrder(Integer order_id) {
        Orders order = ordersRepository.findById(order_id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id: " + order_id));
        ordersRepository.delete(order);
    }

}
