package com.example.tickets_system.dto;

public record LocationInventoryRequest(
        String venueName,
        String address,
        Long totalCapacity
) {
}
