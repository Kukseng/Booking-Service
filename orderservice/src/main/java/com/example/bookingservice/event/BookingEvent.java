package com.example.bookingservice.event;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookingEvent(
        String userId,
        String eventId,
        Long ticketCount,
        BigDecimal totalPrice
) {
}
