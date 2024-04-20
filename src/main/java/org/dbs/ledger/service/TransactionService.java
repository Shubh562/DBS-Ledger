package org.dbs.ledger.service;

import org.dbs.ledger.dto.request.TransactionRequest;
import org.dbs.ledger.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}
