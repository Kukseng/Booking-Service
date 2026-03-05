package com.example.tickets_system.service;

import com.example.tickets_system.dto.InventoryEventRequest;
import com.example.tickets_system.dto.InventoryEventResponse;
import com.example.tickets_system.dto.LocationInventoryRequest;
import com.example.tickets_system.dto.LocationInventoryResponse;

import java.util.List;

public interface InventoryService {

   List<InventoryEventResponse> getAllEvent();

   InventoryEventResponse createEvent(InventoryEventRequest inventoryEventRequest);

   InventoryEventResponse getEventByUuid(String eventId );

   LocationInventoryResponse createVenue(LocationInventoryRequest locationInventoryRequest);

   LocationInventoryResponse getVenueInformation(String venusId);

   void updateEventCapacity(String eventId, Long ticketsBooked);

}
