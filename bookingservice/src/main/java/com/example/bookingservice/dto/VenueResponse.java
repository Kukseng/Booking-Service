package com.example.bookingservice.dto;

public record VenueResponse(
        String  venueId,
        String venueName,
        Long totalCapacity
) {
}
