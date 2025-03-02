package edu.icet.ecom.order.dto;

import edu.icet.ecom.order.util.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer id is required")
        @NotEmpty(message = "Customer id is required")
        @NotBlank(message = "Customer id is required")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
