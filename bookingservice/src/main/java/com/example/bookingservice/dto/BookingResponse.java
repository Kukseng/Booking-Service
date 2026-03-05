package com.example.bookingservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record BookingResponse(
         String userId,
         String eventId,
         Long ticketCount,
        BigDecimal totalPrice
) {
}
