package com.iyzico.challenge.data.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SeatRequestDto {

    @NotBlank(message = "seat.seatNumber.required")
    private String seatNumber;

    @NotNull(message = "seat.price.required")
    private BigDecimal price;

    @NotNull(message = "seat.flightId.required")
    private Long flightId;

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

}
