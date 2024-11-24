package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Seat;
import org.springframework.stereotype.Service;

@Service
public class SeatPurchaseService {

    private final SeatService seatService;

    public SeatPurchaseService(SeatService seatService) {
        this.seatService = seatService;
    }

    public Seat purchaseSeat(long seatId){
        Seat seat = seatService.purchaseSeat(seatId);
        //TODO: implement Iyzico Payment integration.
        return seat;
    }
}
