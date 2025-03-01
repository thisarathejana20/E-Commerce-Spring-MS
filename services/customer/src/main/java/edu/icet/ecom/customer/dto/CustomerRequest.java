package edu.icet.ecom.customer.dto;

import edu.icet.ecom.customer.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    String id,
    @NotNull(message = "First name is required")
    String firstName,
    @NotNull(message = "Last name is required")
    String lastName,
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address")
    String email,
    Address address
    ){
}
