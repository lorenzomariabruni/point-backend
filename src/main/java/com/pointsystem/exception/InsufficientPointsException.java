package com.pointsystem.exception;

public class InsufficientPointsException extends RuntimeException {
    
    public InsufficientPointsException(String message) {
        super(message);
    }
    
    public InsufficientPointsException(String message, Throwable cause) {
        super(message, cause);
    }
}