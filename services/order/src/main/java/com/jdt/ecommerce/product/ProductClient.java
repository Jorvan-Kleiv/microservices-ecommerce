package com.jdt.ecommerce.product;

import com.jdt.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProduct(
            List<PurchaseRequest> requestBody
    )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<PurchaseRequest>> requests = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<PurchaseResponse>> response = restTemplate
                .exchange(
                        productUrl + "/purchase",
                        POST,
                        requests,
                        responseType
                );
        if (response.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the purchase request" + response.getStatusCode());
        }
        return response.getBody();
    }
}
