package com.example.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record InventoryResponse(
        String nameEvent,
        Long leftCapacity,
        Long capacity,
        VenueResponse venueId,
        BigDecimal ticketPrice
) {
}
