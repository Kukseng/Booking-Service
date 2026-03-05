package com.example.bookingservice.dto;

public record BookingRequest(
      String userId,
        String eventId,
        Long ticketCount
) {
}
