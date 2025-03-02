package edu.icet.ecom.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq")
    private Integer id;
    private String name;
    private String  description;
    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Product> products;
}
