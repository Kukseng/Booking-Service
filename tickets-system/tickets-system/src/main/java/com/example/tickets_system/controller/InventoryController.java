package com.example.tickets_system.controller;


import com.example.tickets_system.dto.InventoryEventRequest;
import com.example.tickets_system.dto.InventoryEventResponse;
import com.example.tickets_system.dto.LocationInventoryRequest;
import com.example.tickets_system.dto.LocationInventoryResponse;
import com.example.tickets_system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inventory/events")
    public List<InventoryEventResponse> getAllEvent() {
        return inventoryService.getAllEvent();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inventory/events/{eventId}")
    public InventoryEventResponse inventoryForEvent(@PathVariable String eventId) {
        return inventoryService.getEventByUuid(eventId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/inventory/events")
    public InventoryEventResponse createEvent(@RequestBody InventoryEventRequest inventoryEventRequest) {
        return inventoryService.createEvent(inventoryEventRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/inventory/venue")
    public LocationInventoryResponse createEvent(@RequestBody LocationInventoryRequest locationInventoryRequest) {
        return inventoryService.createVenue(locationInventoryRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/inventory/venue/{venueId}")
   public LocationInventoryResponse inventoryByVenueId(@PathVariable String venueId){
        return inventoryService.getVenueInformation(venueId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/inventory/event/{eventId}/capacity/{capacity}")
    public ResponseEntity<Void> updateEventCapacity(@PathVariable("eventId") String eventId,
                                                    @PathVariable("capacity") Long ticketsBooked) {
        inventoryService.updateEventCapacity(eventId, ticketsBooked);
        return ResponseEntity.ok().build();
    }

}
