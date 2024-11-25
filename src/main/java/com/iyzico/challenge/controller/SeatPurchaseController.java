package com.iyzico.challenge.controller;

import com.iyzico.challenge.data.dto.BankPaymentResponse;
import com.iyzico.challenge.data.dto.SeatPurchaseRequestDto;
import com.iyzico.challenge.sdk.api.ApiResponse;
import com.iyzico.challenge.service.SeatPurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat/purchase")
public class SeatPurchaseController {

    private final SeatPurchaseService seatPurchaseService;

    public SeatPurchaseController(SeatPurchaseService seatPurchaseService) {
        this.seatPurchaseService = seatPurchaseService;
    }

    @PostMapping("/{seatId}")
    public ResponseEntity<ApiResponse<BankPaymentResponse>> purchaseSeat(@PathVariable Long seatId, @RequestBody SeatPurchaseRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(seatPurchaseService.purchaseSeat(seatId, dto),"seat.purchased"));
    }
}
