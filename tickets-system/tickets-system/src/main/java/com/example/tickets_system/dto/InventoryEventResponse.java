package com.example.tickets_system.dto;

import com.example.tickets_system.domain.Venue;

import java.math.BigDecimal;

public record InventoryEventResponse(
        String id,
        String nameEvent,
        Long leftCapacity,
        Long totalCapacity,
        Venue venueId,
        BigDecimal ticketPrice
) {
}
