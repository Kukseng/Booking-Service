package com.example.orderservice.service;

import com.example.bookingservice.event.BookingEvent;
import com.example.orderservice.client.InventoryServiceClient;
import com.example.orderservice.domain.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;
    
    @KafkaListener(topics = "booking", groupId = "order-service", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void orderEvent(BookingEvent bookingEvent){
        log.info("========== Order Event Received: {} ==========", bookingEvent);
        log.info("Processing: userId={}, eventId={}, ticketCount={}", 
            bookingEvent.userId(), bookingEvent.eventId(), bookingEvent.ticketCount());

        try {
            // Create order object
            Order order = createOrder(bookingEvent);
            log.info("Saving Order to database...");
            orderRepository.saveAndFlush(order);
            log.info(" Order created successfully with ID: {}", order.getId());

            // Update Inventory - this should reduce the capacity
            log.info("Calling Inventory Service to reduce capacity...");
            inventoryServiceClient.updateInventory(order.getEventId(), order.getTicketCount());
            log.info("Inventory updated for event: {}, reduced capacity by: {}", order.getEventId(), order.getTicketCount());
            
            log.info("========== Order processing COMPLETED successfully ==========");
        } catch (Exception e) {
            log.error("Failed to process booking event: {}", bookingEvent, e);
            throw new RuntimeException("Failed to process order", e);
        }
    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.userId())
                .eventId(bookingEvent.eventId())
                .ticketCount(bookingEvent.ticketCount())
                .totalPrice(bookingEvent.totalPrice())
                .build();
    }
}

