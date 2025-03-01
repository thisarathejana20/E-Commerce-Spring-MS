package edu.icet.ecom.customer.dto;

import edu.icet.ecom.customer.entity.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
