package com.pointsystem.controller;

import com.pointsystem.dto.CustomerResponse;
import com.pointsystem.dto.PointOperationRequest;
import com.pointsystem.dto.PointOperationResponse;
import com.pointsystem.entity.PointTransaction;
import com.pointsystem.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@Api(tags = "Point Management", description = "API per la gestione dei punti cliente")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/add")
    @ApiOperation(value = "Aggiungi punti", notes = "Aggiunge punti al saldo di un cliente")
    public ResponseEntity<PointOperationResponse> addPoints(
            @Valid @RequestBody @ApiParam("Richiesta di aggiunta punti") PointOperationRequest request) {
        PointOperationResponse response = pointService.addPoints(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/subtract")
    @ApiOperation(value = "Sottrai punti", notes = "Sottrae punti dal saldo di un cliente")
    public ResponseEntity<PointOperationResponse> subtractPoints(
            @Valid @RequestBody @ApiParam("Richiesta di sottrazione punti") PointOperationRequest request) {
        PointOperationResponse response = pointService.subtractPoints(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/balance")
    @ApiOperation(value = "Ottieni saldo cliente", notes = "Restituisce il saldo punti attuale di un cliente")
    public ResponseEntity<CustomerResponse> getCustomerBalance(
            @PathVariable @ApiParam("ID del cliente") Long customerId) {
        CustomerResponse response = pointService.getCustomerBalance(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/transactions")
    @ApiOperation(value = "Ottieni transazioni cliente", notes = "Restituisce lo storico delle transazioni di un cliente")
    public ResponseEntity<List<PointTransaction>> getCustomerTransactions(
            @PathVariable @ApiParam("ID del cliente") Long customerId) {
        List<PointTransaction> transactions = pointService.getCustomerTransactions(customerId);
        return ResponseEntity.ok(transactions);
    }
}