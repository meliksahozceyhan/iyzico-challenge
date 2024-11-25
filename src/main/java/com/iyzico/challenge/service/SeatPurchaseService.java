package com.iyzico.challenge.service;

import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzico.challenge.data.dto.SeatPurchaseRequestDto;
import com.iyzico.challenge.entity.Seat;
import org.springframework.stereotype.Service;

@Service
public class SeatPurchaseService {

    private final SeatService seatService;
    private final IyzicoPaymentIntegrationService iyzicoPaymentIntegrationService;

    public SeatPurchaseService(SeatService seatService, IyzicoPaymentIntegrationService iyzicoPaymentIntegrationService) {
        this.seatService = seatService;
        this.iyzicoPaymentIntegrationService = iyzicoPaymentIntegrationService;
    }

    public BankPaymentResponse purchaseSeat(long seatId, SeatPurchaseRequestDto dto){
        Seat seat = seatService.purchaseSeat(seatId);
        BankPaymentResponse response = iyzicoPaymentIntegrationService.paySeat(seat,dto);
        return response;
    }
}
