package com.iyzico.challenge.service;

import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.core.exception.SeatPurchasedException;
import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.entity.Seat;
import com.iyzico.challenge.mapper.SeatMapper;
import com.iyzico.challenge.repository.SeatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    public SeatService(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }

    @Transactional(readOnly = true)
    public Page<SeatListView> getAllSeatsAndPaginate(Long flightId, Pageable pageable) {
        return seatRepository.findAllByFlightId(flightId, doesRequestHadSortIfNotSortByDefault(pageable));
    }

    @Transactional(readOnly = true)
    public SeatView getSeatById(Long id) {
        return seatRepository.getSeatById(id).orElseThrow(() -> new EntityNotFoundException("seat"));
    }

    public Seat findSeatById(Long id) {
        return seatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("seat"));
    }

    public Seat saveSeat(SeatRequestDto dto) {
        return seatRepository.save(seatMapper.toEntity(dto));
    }

    public Seat updateSeat(Long id, SeatRequestDto dto) {
        return seatRepository.save(seatMapper.updateEntity(findSeatById(id), dto));
    }

    @Transactional
    public Seat purchaseSeat(Long id) {
        Seat seat = seatRepository.findSeatByIdForPurchase(id).orElseThrow(() -> new EntityNotFoundException("seat"));
        if (!seat.getAvailable()) throw new SeatPurchasedException("seat.purchased.exception");

        seat.setAvailable(false);
        seat = seatRepository.save(seat);
        return seat;
    }

    public void deleteSeat(Long id) {
        seatRepository.delete(findSeatById(id));
    }

    private Pageable doesRequestHadSortIfNotSortByDefault(Pageable pageable) {
        if (pageable.getSort().isSorted()) return pageable;
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "updatedAt"));
    }
}
