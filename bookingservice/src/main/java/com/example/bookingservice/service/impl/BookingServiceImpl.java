package com.example.bookingservice.service.impl;

import com.example.bookingservice.client.InventoryServiceClient;
import com.example.bookingservice.domain.Customer;
import com.example.bookingservice.dto.BookingRequest;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.InventoryResponse;
import com.example.bookingservice.event.BookingEvent;
import com.example.bookingservice.respository.CustomerRepository;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final
    CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> KafkaTemplate;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        log.info("========== BOOKING REQUEST RECEIVED ==========");
        log.info("userId: {}, eventId: {}, ticketCount: {}", request.userId(), request.eventId(), request.ticketCount());

        Customer customer = customerRepository.findById(request.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        log.info("✅ Customer found: {}", customer.getName());

//        checking inventory event
        InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.eventId());
        log.info("✅ Inventory Response: {}", inventoryResponse);
        if (inventoryResponse == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Inventory service unavailable or returned null response");
        }

        // Use leftCapacity if available, otherwise fall back to capacity
        Long availableCapacity = inventoryResponse.leftCapacity() != null ? 
            inventoryResponse.leftCapacity() : inventoryResponse.capacity();
            
        if (availableCapacity == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Inventory service returned invalid capacity data");
        }
        
        log.info("Available capacity: {}, Requested: {}", availableCapacity, request.ticketCount());

        if (availableCapacity < request.ticketCount()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Insufficient ticket capacity available");
        }

//        create booking
        BookingEvent bookingEvent = createEventBooking(request,customer,inventoryResponse);
        
        // Send booking event to Kafka asynchronously (non-blocking)
        try {
            log.info("Sending BookingEvent to Kafka topic 'booking'...");
            log.info("BookingEvent details: {}", bookingEvent);
            KafkaTemplate.send("booking", bookingEvent);
            log.info("✅ BookingEvent sent to Kafka successfully!");
        } catch (Exception e) {
            log.warn("❌ Error sending booking event to Kafka (proceeding with booking): {}", e.getMessage());
        }
        
        log.info("========== BOOKING RESPONSE CREATED ==========");
        return BookingResponse.builder()
                .userId(bookingEvent.userId())
                .eventId(bookingEvent.eventId())
                .ticketCount(bookingEvent.ticketCount())
                .totalPrice(bookingEvent.totalPrice())
                .build();
    }

    private BookingEvent createEventBooking(BookingRequest request, Customer customer, InventoryResponse inventoryResponse) {
        BigDecimal ticketPrice = inventoryResponse.ticketPrice() != null ? inventoryResponse.ticketPrice() : BigDecimal.ZERO;
        return BookingEvent.builder()
                .eventId(request.eventId())
                .userId(customer.getCustomerID())
                .ticketCount(request.ticketCount())
                .totalPrice(ticketPrice.multiply(BigDecimal.valueOf(request.ticketCount())))
                .build();
    }
}
