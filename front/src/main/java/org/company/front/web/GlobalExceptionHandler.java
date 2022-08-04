package org.company.front.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.util.exception.AppException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorAttributes errorAttributes;

    /**
     * Handle exception when the DispatcherServlet can't find a handler for a request
     * (when property "throwExceptionIfNoHandlerFound" is set to true).
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("No Handler Found Exception", ex);
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(), ex.getMessage()), status);
    }

    /**
     * Customize the response body of all exception types.
     * @return ResponseEntity with custom response body
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body, @NonNull HttpHeaders headers,@NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("Internal Exception", ex);
        super.handleExceptionInternal(ex, body, headers, status, request);
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(), getRootCause(ex).getMessage()), status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAllUncaughtException(WebRequest request, RuntimeException ex) {
        log.error("Internal Error", ex);
        return createResponseEntity(getDefaultBody(request, ErrorAttributeOptions.of(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, Object>> handleAppException(WebRequest request, AppException ex) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return createResponseEntity(getDefaultBody(request, ex.getOptions(), ex.getMessage()), ex.getStatus());
    }

    // *** Helper functions ***

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponseEntity(Map<String, Object> body, HttpStatus status) {
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
    }

    private Map<String, Object> getDefaultBody(WebRequest request, ErrorAttributeOptions options, String msg) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        if (msg != null) {
            body.put("message", msg);
        }
        return body;
    }

    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
