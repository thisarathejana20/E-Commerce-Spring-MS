package edu.icet.ecom.order.service;

import edu.icet.ecom.order.client.CustomerClient;
import edu.icet.ecom.order.client.ProductClient;
import edu.icet.ecom.order.dto.OrderLineRequest;
import edu.icet.ecom.order.dto.OrderRequest;
import edu.icet.ecom.order.dto.PurchaseRequest;
import edu.icet.ecom.order.repository.OrderRepository;
import edu.icet.ecom.order.util.exception.custom.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest orderRequest) {
        // check if customer exists -- connect to customer microservice
        // here we are calling the customer microservice using Feign client
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot place order :: Customer not found"));

        // purchase the product -- connect to product microservice
        // here we are calling the product microservice using RestTemplate
        productClient.purchaseProducts(orderRequest.products());

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

        return null;
    }
}
