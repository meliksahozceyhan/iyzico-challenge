package com.iyzico.challenge.sdk.data.view;

import java.time.LocalDateTime;

public interface BaseEntityView {
    Long getId();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
