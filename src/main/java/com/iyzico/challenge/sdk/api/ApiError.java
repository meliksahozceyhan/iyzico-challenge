package com.iyzico.challenge.sdk.api;

import com.iyzico.challenge.core.service.LocaleService;

import java.time.LocalDateTime;

public class ApiError {

    private final String errorMessage;

    private final String causeMessage;

    private final LocalDateTime timestamp;

    public ApiError(String errorMessage, String causeMessage) {
        this.errorMessage = errorMessage;
        this.causeMessage = causeMessage;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String errorMessage) {
        this(errorMessage, null);
    }

    public ApiError(){
        this(LocaleService.getMessage("request.error"),null);
    }

    public static ApiError error(String errorMessage){
        return new ApiError(LocaleService.getMessage(errorMessage),null);
    }

    public static ApiError error(String errorMessage, String causeMessage){
        return new ApiError(LocaleService.getMessage(errorMessage),LocaleService.getMessage(causeMessage));
    }

    public static ApiError errorWithoutCauseTranslation(String errorMessage, String causeMessage){
        return new ApiError(LocaleService.getMessage(errorMessage),causeMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
