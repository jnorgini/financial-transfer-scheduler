package com.norgini.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBusinessException(BusinessException ex) {
        return buildApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException ex) {
        
        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
        		.getFieldErrors().stream()
                .map(error -> ApiError.FieldError.builder()
                        .name(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        String principalMessage = ex.getBindingResult()
        		.getFieldErrors()
        		.stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Erro de validação nos campos preenchidos.");

        return buildApiError(HttpStatus.BAD_REQUEST, principalMessage, fieldErrors);
    }

    private ApiError buildApiError(HttpStatus status, String message, List<ApiError.FieldError> fields) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .fields(fields)
                .build();
    }
    
}
