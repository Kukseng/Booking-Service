package com.example.tickets_system.service.Impl;

import com.example.tickets_system.domain.Event;
import com.example.tickets_system.domain.Venue;
import com.example.tickets_system.dto.InventoryEventRequest;
import com.example.tickets_system.dto.InventoryEventResponse;
import com.example.tickets_system.dto.LocationInventoryRequest;
import com.example.tickets_system.dto.LocationInventoryResponse;
import com.example.tickets_system.mapper.EventMapper;
import com.example.tickets_system.repository.EventRepository;
import com.example.tickets_system.repository.VenusRepository;

import com.example.tickets_system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final EventRepository eventRepository;
    private final VenusRepository venusRepository;
    private final EventMapper eventMapper;

//    get all Event
    @Override
    public List<InventoryEventResponse> getAllEvent() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toInventoryEventResponse).toList()
                ;
    }

    @Override
    public InventoryEventResponse createEvent(InventoryEventRequest inventoryEventRequest) {
        if (eventRepository.existsByNameEvent(inventoryEventRequest.nameEvent())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event already exists");

        }
       Venue venue= venusRepository.findByVenueId(inventoryEventRequest.venueId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found")
        );

        if (inventoryEventRequest.totalCapacity() > venue.getTotalCapacity()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Total capacity exceeded");
        }


        Event event = eventMapper.fromInventoryEventRequestToEvent(inventoryEventRequest);
        event.setVenueId(venue);
        event = eventRepository.save(event);

        return eventMapper.toInventoryEventResponse(event);
    }

    @Override
    public InventoryEventResponse getEventByUuid(String eventId) {

        return eventRepository.findById(eventId).map(eventMapper::toInventoryEventResponse).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"UUID not found")
        );
    }

    @Override
    public LocationInventoryResponse createVenue(LocationInventoryRequest locationInventoryRequest) {

        Venue venue= eventMapper.fromLocationInventoryRequestToVenue(locationInventoryRequest);
        venue = venusRepository.save(venue);

        return eventMapper.toLocationInventoryResponse(venue);
    }


    @Override
    public LocationInventoryResponse getVenueInformation(String venusId) {

        return venusRepository.findByVenueId(venusId).map(eventMapper::toLocationInventoryResponse).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.OK,"Venus Id not found"));
    }

    @Override
    public void updateEventCapacity(String eventId, Long ticketsBooked) {

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")
        );

        event.setTotalCapacity(event.getTotalCapacity()-ticketsBooked);
        eventRepository.save(event);


    }


}
