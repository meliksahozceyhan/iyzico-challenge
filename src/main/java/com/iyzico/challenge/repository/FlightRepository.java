package com.iyzico.challenge.repository;

import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f from flight f WHERE f.id = :id")
    Optional<FlightView> getFlightWithAvailableSeats(Long id);

    @Query("SELECT f from flight f")
    Page<FlightView> getAllFlightsWithSeatsAvailable(Pageable pageable);
}
