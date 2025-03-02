package edu.icet.ecom.order.client;

import edu.icet.ecom.order.dto.PurchaseRequest;
import edu.icet.ecom.order.dto.PurchaseResponse;
import edu.icet.ecom.order.util.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        // first set the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        // application/json
        httpHeaders.set(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON_VALUE);

        // then we need to set the request type
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequests, httpHeaders);

        // then we need to set the response type
        // by creating this object and passing it the callback will cast the response to the correct type
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("Failed to purchase products. Try Again.");
        }

        return responseEntity.getBody();
    }
}
