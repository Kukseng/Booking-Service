package com.example.orderservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceClient {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    
    private final RestTemplate restTemplate;

    public void updateInventory(String eventId, Long ticketCount) {
        try {
            String url = inventoryServiceUrl + "/event/" + eventId + "/capacity/" + ticketCount;
            log.info(" Calling inventory service URL: {}", url);
            log.info(" Attempting to reduce capacity by: {} tickets", ticketCount);
            restTemplate.put(url, null);
            log.info(" Successfully reduced capacity for event: {} by: {} tickets", eventId, ticketCount);
        } catch (Exception e) {
            log.error(" Error updating inventory for event: {}, ticket count: {}", eventId, ticketCount, e);
            throw new RuntimeException("Failed to update inventory", e);
        }
    }


}
