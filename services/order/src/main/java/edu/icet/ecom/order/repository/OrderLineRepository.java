package edu.icet.ecom.order.repository;

import edu.icet.ecom.order.dto.OrderLineResponse;
import edu.icet.ecom.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    List<OrderLine> findAllByOrderId(Integer id);
}
