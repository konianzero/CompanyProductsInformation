package org.company.cache.exception;

// TODO - Deal with exceptions in cache and controller modules
public class WaitingTimeExceededException extends Exception {
    public WaitingTimeExceededException(String msg) {
        super(msg);
    }
}
