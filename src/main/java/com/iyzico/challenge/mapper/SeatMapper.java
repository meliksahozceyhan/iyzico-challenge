package com.iyzico.challenge.mapper;

import com.iyzico.challenge.core.exception.BadRequestException;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.service.FlightService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SeatMapper {

    private final FlightService flightService;

    public SeatMapper(FlightService flightService) {
        this.flightService = flightService;
    }

    public Seat toEntity(SeatRequestDto dto){
        Seat entity = new Seat();
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setPrice(dto.getPrice());
        entity.setFlight(flightService.findFlightById(dto.getFlightId()));
        return entity;
    }

    public Seat updateEntity(Seat entity, SeatRequestDto dto){
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setPrice(dto.getPrice());
        entity.setFlight(flightService.findFlightById(dto.getFlightId()));
        return entity;
    }
}
