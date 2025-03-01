package edu.icet.ecom.customer.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
@Builder
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
