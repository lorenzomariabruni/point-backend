package com.pointsystem.dto;

import com.pointsystem.enums.TransactionType;
import java.time.LocalDateTime;

public class PointOperationResponse {

    private Long transactionId;
    private Long customerId;
    private TransactionType operation;
    private Integer points;
    private Integer newBalance;
    private String description;
    private LocalDateTime timestamp;
    private boolean success;
    private String message;

    // Constructors
    public PointOperationResponse() {}

    public PointOperationResponse(Long transactionId, Long customerId, TransactionType operation, 
                                Integer points, Integer newBalance, String description, 
                                LocalDateTime timestamp, boolean success, String message) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.operation = operation;
        this.points = points;
        this.newBalance = newBalance;
        this.description = description;
        this.timestamp = timestamp;
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public TransactionType getOperation() {
        return operation;
    }

    public void setOperation(TransactionType operation) {
        this.operation = operation;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Integer newBalance) {
        this.newBalance = newBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}