package com.iyzico.challenge.mapper;

import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public Flight toEntity(FlightRequestDto dto) {
        Flight entity = new Flight();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFromLocation(dto.getFromLocation());
        entity.setToLocation(dto.getToLocation());
        return entity;
    }

    public Flight updateEntity(Flight entity, FlightRequestDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFromLocation(dto.getFromLocation());
        entity.setToLocation(dto.getToLocation());
        return entity;
    }
}
