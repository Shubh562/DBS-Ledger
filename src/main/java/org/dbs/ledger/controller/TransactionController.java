package org.dbs.ledger.controller;

import jakarta.validation.Valid;
import org.dbs.ledger.dto.request.TransactionRequest;
import org.dbs.ledger.dto.response.TransactionResponse;
import org.dbs.ledger.dto.response.wrapper.ResponseWrapper;
import org.dbs.ledger.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<TransactionResponse>> performTransaction(
            @RequestBody @Valid TransactionRequest transactionRequest
    ) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(transactionResponse));
    }
}
