package com.example.tickets_system.mapper;

import com.example.tickets_system.domain.Event;
import com.example.tickets_system.domain.Venue;
import com.example.tickets_system.dto.InventoryEventRequest;
import com.example.tickets_system.dto.InventoryEventResponse;
import com.example.tickets_system.dto.LocationInventoryRequest;
import com.example.tickets_system.dto.LocationInventoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    InventoryEventResponse toInventoryEventResponse(Event event);

    LocationInventoryResponse toLocationInventoryResponse(Venue venue);
    @Mapping(target = "venueId", ignore = true)
    Event fromInventoryEventRequestToEvent(InventoryEventRequest inventoryEventRequest);

    Venue fromLocationInventoryRequestToVenue(LocationInventoryRequest inventoryEventRequest);

}
