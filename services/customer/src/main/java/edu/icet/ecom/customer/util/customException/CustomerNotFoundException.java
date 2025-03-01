package edu.icet.ecom.customer.util.customException;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {
    private final String message;
}
