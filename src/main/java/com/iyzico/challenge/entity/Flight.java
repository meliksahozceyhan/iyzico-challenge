package com.iyzico.challenge.entity;

import com.iyzico.challenge.sdk.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "flight")
@Table(name = "flight")
public class Flight extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "flight", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Seat> seats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
