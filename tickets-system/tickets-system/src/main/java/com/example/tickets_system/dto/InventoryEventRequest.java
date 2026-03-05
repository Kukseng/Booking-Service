package com.example.tickets_system.dto;

import java.math.BigDecimal;

public record InventoryEventRequest(
        String venueId ,
            String nameEvent,
            Long totalCapacity,
            Long leftCapacity,
        BigDecimal ticketPrice
) {
}
