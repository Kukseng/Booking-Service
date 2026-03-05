package com.example.tickets_system.repository;

import com.example.tickets_system.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface VenusRepository extends JpaRepository<Venue,String> {

    Optional<Venue> findByVenueId(String venueId);
     Boolean existsByVenueId(String venueId);
}
