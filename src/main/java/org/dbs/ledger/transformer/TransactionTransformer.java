package org.dbs.ledger.transformer;

import org.dbs.ledger.annotation.Transformer;
import org.dbs.ledger.dto.request.TransactionRequest;
import org.dbs.ledger.dto.response.TransactionResponse;
import org.dbs.ledger.model.input.AccountBalanceUpdateInput;
import org.dbs.ledger.model.input.AccountEntryInput;
import org.dbs.ledger.model.output.AccountBalanceOutput;
import org.dbs.ledger.model.output.AccountEntryOutput;

@Transformer
public class TransactionTransformer {
    public AccountBalanceUpdateInput convertTransactionRequestToAccountInput(TransactionRequest transactionRequest, String fromAccountId) {
        return new AccountBalanceUpdateInput(
                fromAccountId,
                transactionRequest.getToAccountId(),
                transactionRequest.getTransactionType(),
                transactionRequest.getAmount()
        );
    }

    public AccountEntryInput convertTransactionRequestToEntryInput(TransactionRequest transactionRequest, String fromAccountId) {
        return new AccountEntryInput(
                fromAccountId,
                transactionRequest.getToAccountId(),
                transactionRequest.getTransactionType(),
                transactionRequest.getAmount()
        );
    }

    public TransactionResponse convertAccountBalanceAndEntryToResponse(AccountBalanceOutput accountBalanceOutput, AccountEntryOutput accountEntry) {
        return TransactionResponse
                .builder()
                .accountId(accountBalanceOutput.accountBalance().accountId())
                .transactionId(accountEntry.accountEntryId())
                .availableBalance(accountBalanceOutput.accountBalance().availableBalance())
                .build();
    }
}
