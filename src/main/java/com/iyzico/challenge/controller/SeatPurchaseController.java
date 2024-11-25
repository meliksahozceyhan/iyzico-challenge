package com.iyzico.challenge.controller;

import com.iyzico.challenge.sdk.api.ApiResponse;
import com.iyzico.challenge.service.SeatPurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seat/purchase")
public class SeatPurchaseController {

    private final SeatPurchaseService seatPurchaseService;

    public SeatPurchaseController(SeatPurchaseService seatPurchaseService) {
        this.seatPurchaseService = seatPurchaseService;
    }

    @PostMapping("/{seatId}")
    public ResponseEntity<ApiResponse<Long>> purchaseSeat(@PathVariable Long seatId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(seatPurchaseService.purchaseSeat(seatId).getId(),"seat.purchased"));
    }
}
