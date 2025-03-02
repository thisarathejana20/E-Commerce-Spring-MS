package edu.icet.ecom.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

// we need to include this annotation because this record is going to be included inside PaymentRequest object
// that PaymentRequest object is annotated with @Valid in REST controller
// when you use complex record inside another record, you need to include this annotation
// then along with parent record or class child class can be annotated with @Valid
@Validated
public record Customer(
        String id,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Invalid email address")
        String email
) {
}
