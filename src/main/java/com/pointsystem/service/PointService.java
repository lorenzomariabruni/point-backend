package com.pointsystem.service;

import com.pointsystem.dto.CustomerResponse;
import com.pointsystem.dto.PointOperationRequest;
import com.pointsystem.dto.PointOperationResponse;
import com.pointsystem.entity.PointTransaction;
import java.util.List;

public interface PointService {
    
    PointOperationResponse addPoints(PointOperationRequest request);
    
    PointOperationResponse subtractPoints(PointOperationRequest request);
    
    CustomerResponse getCustomerBalance(Long customerId);
    
    List<PointTransaction> getCustomerTransactions(Long customerId);
}