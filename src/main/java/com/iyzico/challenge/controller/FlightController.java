package com.iyzico.challenge.controller;

import com.iyzico.challenge.data.dto.FlightRequestDto;
import com.iyzico.challenge.data.view.FlightView;
import com.iyzico.challenge.sdk.api.ApiResponse;
import com.iyzico.challenge.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<FlightView>>> getAllAndPaginate(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(flightService.getAllFlightsWithAvailableSeats(pageable), "flight.success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightView>> getOneById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(flightService.getFlightWithAvailableSeatsById(id), "flight.success"));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Long>> saveFlight(@RequestBody @Valid FlightRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(flightService.saveFlight(dto).getId(), "flight.created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(flightService.updateFlight(id, dto).getId(), "flight.updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, "flight.deleted"));
    }
}
