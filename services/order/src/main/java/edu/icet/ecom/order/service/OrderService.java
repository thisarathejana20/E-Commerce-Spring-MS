package edu.icet.ecom.order.service;

import edu.icet.ecom.order.client.CustomerClient;
import edu.icet.ecom.order.client.ProductClient;
import edu.icet.ecom.order.dto.*;
import edu.icet.ecom.order.producer.kafka.OrderProducer;
import edu.icet.ecom.order.repository.OrderRepository;
import edu.icet.ecom.order.util.exception.custom.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest orderRequest) {
        // check if customer exists -- connect to customer microservice
        // here we are calling the customer microservice using Feign client
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot place order :: Customer not found"));

        // purchase the product -- connect to product microservice
        // here we are calling the product microservice using RestTemplate
        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());

        // create the order and save
        var order = orderRepository.save(orderMapper.toOrder(orderRequest));

        // to save an order line request
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // payment process


        // send email -- kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .toList();
    }

    public OrderResponse findOrderById(Integer id) {
        return orderRepository.
                findById(id).
                map(orderMapper::fromOrder).
                orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order with id %d not found", id)
                ));
    }
}
