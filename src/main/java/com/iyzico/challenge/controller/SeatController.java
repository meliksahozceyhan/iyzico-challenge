package com.iyzico.challenge.controller;

import com.iyzico.challenge.data.dto.SeatRequestDto;
import com.iyzico.challenge.data.view.SeatListView;
import com.iyzico.challenge.data.view.SeatView;
import com.iyzico.challenge.sdk.api.ApiResponse;
import com.iyzico.challenge.service.SeatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatView>> getSeat(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(seatService.getSeatById(id), "seat.success"));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ApiResponse<Page<SeatListView>>> getSeatsOfFlight(@PathVariable Long flightId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(seatService.getAllSeatsAndPaginate(flightId, pageable), "seat.success"));
    }


    @PostMapping()
    public ResponseEntity<ApiResponse<Long>> saveSeat(@RequestBody @Valid SeatRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(seatService.saveSeat(dto).getId(), "seat.created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateSeat(@PathVariable Long id, @RequestBody @Valid SeatRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(seatService.updateSeat(id, dto).getId(), "seat.updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, "seat.deleted"));
    }
}
