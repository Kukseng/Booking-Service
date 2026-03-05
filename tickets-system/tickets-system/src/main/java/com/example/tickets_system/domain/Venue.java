package com.example.tickets_system.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String venueId;

    @Column(name = "name")
    private String venueName;

    @Column(name = "total_capacity")
    private Long totalCapacity;

    @Column(name = "address")
    private String address;

}
