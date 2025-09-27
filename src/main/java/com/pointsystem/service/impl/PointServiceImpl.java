package com.pointsystem.service.impl;

import com.pointsystem.dto.CustomerResponse;
import com.pointsystem.dto.PointOperationRequest;
import com.pointsystem.dto.PointOperationResponse;
import com.pointsystem.entity.Customer;
import com.pointsystem.entity.PointTransaction;
import com.pointsystem.enums.TransactionType;
import com.pointsystem.exception.CustomerNotFoundException;
import com.pointsystem.exception.InsufficientPointsException;
import com.pointsystem.repository.CustomerRepository;
import com.pointsystem.repository.PointTransactionRepository;
import com.pointsystem.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PointServiceImpl implements PointService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PointTransactionRepository transactionRepository;

    @Override
    public PointOperationResponse addPoints(PointOperationRequest request) {
        Customer customer = findCustomerById(request.getCustomerId());
        
        // Aggiorna il saldo punti
        Integer newBalance = customer.getPoints() + request.getPoints();
        customer.setPoints(newBalance);
        customerRepository.save(customer);
        
        // Crea la transazione
        PointTransaction transaction = new PointTransaction(
            request.getCustomerId(),
            TransactionType.ADD,
            request.getPoints(),
            request.getDescription()
        );
        transaction = transactionRepository.save(transaction);
        
        return new PointOperationResponse(
            transaction.getId(),
            request.getCustomerId(),
            TransactionType.ADD,
            request.getPoints(),
            newBalance,
            request.getDescription(),
            LocalDateTime.now(),
            true,
            "Punti aggiunti con successo"
        );
    }

    @Override
    public PointOperationResponse subtractPoints(PointOperationRequest request) {
        Customer customer = findCustomerById(request.getCustomerId());
        
        // Verifica se il cliente ha abbastanza punti
        if (customer.getPoints() < request.getPoints()) {
            throw new InsufficientPointsException(
                "Punti insufficienti. Saldo attuale: " + customer.getPoints() + 
                ", richiesti: " + request.getPoints()
            );
        }
        
        // Aggiorna il saldo punti
        Integer newBalance = customer.getPoints() - request.getPoints();
        customer.setPoints(newBalance);
        customerRepository.save(customer);
        
        // Crea la transazione
        PointTransaction transaction = new PointTransaction(
            request.getCustomerId(),
            TransactionType.SUBTRACT,
            request.getPoints(),
            request.getDescription()
        );
        transaction = transactionRepository.save(transaction);
        
        return new PointOperationResponse(
            transaction.getId(),
            request.getCustomerId(),
            TransactionType.SUBTRACT,
            request.getPoints(),
            newBalance,
            request.getDescription(),
            LocalDateTime.now(),
            true,
            "Punti sottratti con successo"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerBalance(Long customerId) {
        Customer customer = findCustomerById(customerId);
        return new CustomerResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointTransaction> getCustomerTransactions(Long customerId) {
        // Verifica che il cliente esista
        findCustomerById(customerId);
        return transactionRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Cliente con ID " + customerId + " non trovato"
            ));
    }
}