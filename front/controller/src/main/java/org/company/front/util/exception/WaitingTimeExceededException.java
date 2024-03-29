package org.company.front.util.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.EXCEPTION;

// TODO - Deal with exceptions in cache and controller modules
public class WaitingTimeExceededException extends AppException {
    public WaitingTimeExceededException(String msg) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, msg, ErrorAttributeOptions.of(EXCEPTION));
    }
}
