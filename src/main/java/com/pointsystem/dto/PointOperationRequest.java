package com.pointsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PointOperationRequest {

    @NotNull(message = "Customer ID è obbligatorio")
    private Long customerId;

    @NotNull(message = "Punti sono obbligatori")
    @Positive(message = "I punti devono essere positivi")
    private Integer points;

    private String description;

    // Constructors
    public PointOperationRequest() {}

    public PointOperationRequest(Long customerId, Integer points, String description) {
        this.customerId = customerId;
        this.points = points;
        this.description = description;
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}