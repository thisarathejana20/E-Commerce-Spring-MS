package edu.icet.ecom.order.service;

import edu.icet.ecom.order.dto.OrderRequest;
import edu.icet.ecom.order.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .reference(orderRequest.reference())
                .build();
    }
}
