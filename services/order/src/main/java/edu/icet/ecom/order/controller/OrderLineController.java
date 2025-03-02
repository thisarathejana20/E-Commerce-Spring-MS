package edu.icet.ecom.order.controller;

import edu.icet.ecom.order.dto.OrderLineResponse;
import edu.icet.ecom.order.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-lines")
public class OrderLineController {
    private final OrderLineService orderLineService;

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(@PathVariable Integer id) {
        return ResponseEntity.ok(orderLineService.findAllByOrderId(id));
    }
}
