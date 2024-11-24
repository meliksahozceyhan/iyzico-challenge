package com.iyzico.challenge.data.view;

import com.iyzico.challenge.sdk.data.view.BaseEntityView;

import java.math.BigDecimal;

public interface SeatView extends BaseEntityView {

    String getSeatNumber();

    BigDecimal getPrice();

    Boolean getIsAvailable();

    FlightListView getFlight();
}
