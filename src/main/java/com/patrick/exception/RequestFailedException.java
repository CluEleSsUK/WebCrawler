package com.patrick.exception;

public class RequestFailedException extends Exception {

    private static final String message = "Invalid URL supplied";

    public RequestFailedException() {
        super(message);
    }

    public RequestFailedException(Throwable t) {
        super(message, t);
    }

    public RequestFailedException(String newMessage) {
        super(newMessage);
    }
}
