package com.codehows.zegozero.service;

import com.codehows.zegozero.dto.Finished_product_management_Dto;
import com.codehows.zegozero.dto.Order_Dto;
import com.codehows.zegozero.dto.Shipment_management_dto;
import com.codehows.zegozero.entity.Finish_product;
import com.codehows.zegozero.entity.Orders;
import com.codehows.zegozero.repository.FinishProductRepository;
import com.codehows.zegozero.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class finishedProductService {

    private final FinishProductRepository finishProductRepository;
    private final OrdersRepository ordersRepository;

    public void receivesave(Finished_product_management_Dto productDto) {
        Optional<Orders> optionalOrder = ordersRepository.findById(productDto.getOrder_id());

        if (optionalOrder.isPresent()) {
           Orders order = optionalOrder.get();

           Integer production_quantity = order.getQuantity();
           Integer real_quantity = productDto.getReceived_quantity();
           Integer inventory_quantity = real_quantity - production_quantity;
           if (inventory_quantity > 0) {
               // 입고
               Finish_product finishProduct = new Finish_product();
               finishProduct.setProduct_name(productDto.getProduct_name());
               finishProduct.setOrder_id(order);
               finishProduct.setReceived_quantity(production_quantity);
               finishProduct.setReceived_date(new Date());
               finishProductRepository.save(finishProduct);

               Finish_product finishProduct2 = new Finish_product();
               finishProduct2.setProduct_name(productDto.getProduct_name());
               finishProduct2.setReceived_quantity(inventory_quantity);
               finishProduct2.setReceived_date(new Date());
               finishProductRepository.save(finishProduct2);

           } else {
               // 입고
               Finish_product finishProduct = new Finish_product();
               finishProduct.setProduct_name(productDto.getProduct_name());
               finishProduct.setOrder_id(order);
               finishProduct.setReceived_quantity(production_quantity);
               finishProduct.setReceived_date(new Date());
               finishProductRepository.save(finishProduct);
           }

        }
    }

    public void shippingsave(Shipment_management_dto shippingProduct) {
        Optional<Orders> optionalOrder = ordersRepository.findById(shippingProduct.getOrder_id());

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

                // 출고
                Finish_product finishProduct = new Finish_product();
                finishProduct.setProduct_name(order.getProduct_name());
                finishProduct.setOrder_id(order);
                finishProduct.setShipped_date(new Date());
                finishProduct.setShipped_quantity(order.getQuantity());
                finishProductRepository.save(finishProduct);

        }
    }

    public List<Finish_product> findAll() {
        return finishProductRepository.findAll();
    }

    // 입고만 찾는 메서드
    public List<Finish_product> findByShippedQuantityIsNull() {
        return finishProductRepository.findByShippedQuantityIsNull();
    }

    // 출고만 찾는 메서드
    public List<Finish_product> findByReceivedQuantityIsNull() {
        return finishProductRepository.findByReceivedQuantityIsNull();
    }

}
