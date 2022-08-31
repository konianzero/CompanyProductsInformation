package org.company.cache.exception;

public class WaitingTimeExceededException extends Exception {
    public WaitingTimeExceededException(String msg) {
        super(msg);
    }
}
