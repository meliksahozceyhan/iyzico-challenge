package com.iyzico.challenge.service;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.entity.Flight;
import com.iyzico.challenge.mapper.FlightMapper;
import com.iyzico.challenge.repository.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FlightService {

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Transactional(readOnly = true)
    public FlightView getFlightWithAvailableSeatsById(Long id){
        return flightRepository.getFlightWithAvailableSeats(id).orElseThrow(()-> new EntityNotFoundException("flight"));
    }

    @Transactional(readOnly = true)
    public Page<FlightView> getAllFlightsWithAvailableSeats(Pageable pageable){
        return flightRepository.getAllFlightsWithSeatsAvailable(doesRequestHadSortIfNotSortByDefault(pageable));
    }

    public Flight findFlightById(Long id){
        return flightRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("flight"));
    }

    public Flight saveFlight(FlightRequestDto dto){
        return flightRepository.save(flightMapper.toEntity(dto));
    }

    public Flight updateFlight(Long id, FlightRequestDto dto){
        return flightRepository.save(flightMapper.updateEntity(findFlightById(id),dto));
    }

    public void deleteFlight(Long id){
        flightRepository.delete(findFlightById(id));
    }

    private Pageable doesRequestHadSortIfNotSortByDefault(Pageable pageable){
        if(pageable.getSort().isSorted()) return pageable;
        return PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC,"updatedAt"));
    }
}
