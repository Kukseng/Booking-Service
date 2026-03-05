package com.example.bookingservice.client;

import com.example.bookingservice.dto.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String inventoryUrl;
    
    private final RestTemplate restTemplate;
    
    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryResponse getInventory(String eventId){
        try {
            String url = inventoryUrl + "/" + eventId;
            log.info("Calling inventory service at URL: {}", url);
            
            ResponseEntity<InventoryResponse> response = restTemplate.getForEntity(url, InventoryResponse.class);
            InventoryResponse inventoryResponse = response.getBody();
            
            log.info("Inventory service response: {}", inventoryResponse);
            
            if (inventoryResponse == null) {
                log.error("Inventory service returned null response for event: {}", eventId);
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, 
                    "Inventory service returned empty response");
            }
            
            return inventoryResponse;
        } catch (RestClientException e) {
            log.error("Failed to connect to inventory service: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, 
                "Unable to connect to inventory service");
        }
    }
}
