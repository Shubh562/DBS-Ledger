package org.dbs.ledger.service.impl;

import org.dbs.ledger.configuration.contexts.AccountContext;
import org.dbs.ledger.dto.request.TransactionRequest;
import org.dbs.ledger.dto.response.TransactionResponse;
import org.dbs.ledger.dto.response.wrapper.ErrorResponse;
import org.dbs.ledger.enums.AccountBalanceOutputStatus;
import org.dbs.ledger.enums.AccountEntryOutputStatus;
import org.dbs.ledger.enums.ErrorCode;
import org.dbs.ledger.exceptions.RestException;
import org.dbs.ledger.helper.AccountEntryHelper;
import org.dbs.ledger.helper.AccountHelper;
import org.dbs.ledger.model.output.AccountBalanceOutput;
import org.dbs.ledger.model.output.AccountEntryOutput;
import org.dbs.ledger.service.TransactionService;
import org.dbs.ledger.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionTransformer transactionTransformer;

    private final AccountHelper accountHelper;

    private final AccountEntryHelper accountEntryHelper;

    private final AccountContext accountContext;

    @Autowired
    public TransactionServiceImpl(TransactionTransformer transactionTransformer, AccountHelper accountHelper, AccountEntryHelper accountEntryHelper, AccountContext accountContext) {
        this.transactionTransformer = transactionTransformer;
        this.accountHelper = accountHelper;
        this.accountEntryHelper = accountEntryHelper;
        this.accountContext = accountContext;
    }

    @Override
//    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        AccountBalanceOutput accountBalanceOutput = accountHelper.updateAccountBalance(transactionTransformer.convertTransactionRequestToAccountInput(transactionRequest, accountContext.getAccountId()));
        if (!accountBalanceOutput.accountBalanceOutputStatus().equals(AccountBalanceOutputStatus.SUCCESS)) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
        }

        AccountEntryOutput accountEntry = accountEntryHelper.createAccountEntry(transactionTransformer.convertTransactionRequestToEntryInput(transactionRequest, accountContext.getAccountId()));
        if (!accountEntry.accountEntryOutputStatus().equals(AccountEntryOutputStatus.SUCCESS)) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
        }


        return transactionTransformer.convertAccountBalanceAndEntryToResponse(accountBalanceOutput, accountEntry);
    }
}
