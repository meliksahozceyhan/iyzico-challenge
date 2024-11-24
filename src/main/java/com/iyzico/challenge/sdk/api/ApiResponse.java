package com.iyzico.challenge.sdk.api;

import com.iyzico.challenge.core.service.LocaleService;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private final T data;

    private final String message;

    private final LocalDateTime timestamp;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public T getData(){return data;}

    public LocalDateTime getTimestamp(){return timestamp;}

    public String getMessage() {
        return message;
    }

    public static <T> ApiResponse<T> success(T data){return success(data,LocaleService.getMessage("request.success"));}

    public static <T> ApiResponse<T> success(T data, String message){
        return new ApiResponse<>(data, LocaleService.getMessage(message));
    }
}
