package com.iyzico.challenge.data.view;

import com.iyzico.challenge.sdk.data.view.BaseEntityView;

public interface FlightListView extends BaseEntityView {

    String getName();

    String getFromLocation();

    String getToLocation();

    String getDescription();


}
