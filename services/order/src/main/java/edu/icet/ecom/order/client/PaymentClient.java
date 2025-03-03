package edu.icet.ecom.order.client;

import edu.icet.ecom.order.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "payment",
    url = "${application.config.payment-url}"
)
public interface PaymentClient {

    @PostMapping
    Integer requestOrderPayment(@RequestBody PaymentRequest paymentRequest);
}
