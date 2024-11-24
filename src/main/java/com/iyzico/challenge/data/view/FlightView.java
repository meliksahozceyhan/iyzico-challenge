package com.iyzico.challenge.data.view;

import com.iyzico.challenge.sdk.data.view.BaseEntityView;

import java.util.List;

public interface FlightView extends BaseEntityView {
    String getName();

    String getFromLocation();

    String getToLocation();

    String getDescription();

    List<SeatListView> getSeats();
}
