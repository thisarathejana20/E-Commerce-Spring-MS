package edu.icet.ecom.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    // join column name is order_id
    // if not provided it will be order_id also
    // hibernate take below variable name and append it with _id
    private Order order;
    private Integer productId;
    private Double quantity;
}
