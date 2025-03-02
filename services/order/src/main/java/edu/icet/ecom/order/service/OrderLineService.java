package edu.icet.ecom.order.service;

import edu.icet.ecom.order.dto.OrderLineRequest;
import edu.icet.ecom.order.dto.OrderLineResponse;
import edu.icet.ecom.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer id) {
        return orderLineRepository.findAllByOrderId(id)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
