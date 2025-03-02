package edu.icet.ecom.order.controller;

import edu.icet.ecom.order.dto.OrderRequest;
import edu.icet.ecom.order.dto.OrderResponse;
import edu.icet.ecom.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
}
