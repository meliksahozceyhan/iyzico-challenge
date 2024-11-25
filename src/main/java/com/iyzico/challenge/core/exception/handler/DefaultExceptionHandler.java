package com.iyzico.challenge.core.exception.handler;

import com.iyzico.challenge.core.exception.BadRequestException;
import com.iyzico.challenge.core.exception.EntityNotFoundException;
import com.iyzico.challenge.sdk.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalExceptions(Exception exception) {
        System.out.println("exception.getMessage() = " + exception.getMessage());
        System.out.println(Exception.class.getName() + " is thrown");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError.errorWithoutCauseTranslation("error.unexpected",exception.getMessage()));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex){
        ApiError response = ex.getCauseMessage() != null
                ? ApiError.error(ex.getMessage(),ex.getCauseMessage())
                : ApiError.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException exception){
        ApiError response = exception.getField() != null
                ? ApiError.error(exception.getMessage(),exception.getField())
                : ApiError.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiError response = ApiError.error(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(), exception.getObjectName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
