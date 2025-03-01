package edu.icet.ecom.customer.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Validated
@Builder
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
