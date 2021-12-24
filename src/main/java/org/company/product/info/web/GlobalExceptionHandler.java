package org.company.product.info.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.product.info.util.exception.*;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("No Handler Found Exception", ex);
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getLocalizedMessage());
        map.put("error", status.getReasonPhrase());
        map.put("status", status.value());
        return ResponseEntity.status(status).body(map);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> appException(AppException ex, WebRequest req) {
        log.error("Application Exception", ex);
        Map<String, Object> body = errorAttributes.getErrorAttributes(req, ex.getOptions());
        HttpStatus status = ex.getStatus();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body, @NonNull HttpHeaders headers, HttpStatus status, @NonNull WebRequest request) {
        log.error("Internal Exception", ex);
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getLocalizedMessage());
        map.put("error", status.getReasonPhrase());
        map.put("status", status.value());
        return ResponseEntity.status(status).body(map);
    }
}
