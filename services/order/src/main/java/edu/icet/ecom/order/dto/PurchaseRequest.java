package edu.icet.ecom.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product id is required")
        Integer productId,
        @Positive(message = "Quantity must be positive")
        Double quantity
) {
}
