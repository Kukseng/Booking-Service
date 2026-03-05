package com.example.tickets_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column( nullable = false)
    private String nameEvent;

    @Column( nullable = false)
    private Long totalCapacity;

    @Column( nullable = false)
    private Long leftCapacity;

    @Column(name= "ticket_price")
    private BigDecimal ticketPrice;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venueId;
}