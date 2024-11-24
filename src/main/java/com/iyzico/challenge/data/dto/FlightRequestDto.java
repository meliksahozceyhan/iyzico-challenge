package com.iyzico.challenge.data.dto;

import javax.validation.constraints.NotBlank;

public class FlightRequestDto {
    @NotBlank(message = "flight.name.required")
    private String name;

    private String fromLocation;

    private String toLocation;

    @NotBlank(message = "flight.description.required")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
