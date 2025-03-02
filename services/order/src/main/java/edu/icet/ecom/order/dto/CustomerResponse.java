package edu.icet.ecom.order.dto;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
