package edu.icet.ecom.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is required")
    Integer productId,
    @NotNull(message = "Quantity is required")
    Double quantity
) {
}
