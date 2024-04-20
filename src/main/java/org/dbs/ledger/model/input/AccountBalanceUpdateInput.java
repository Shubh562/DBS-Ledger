package org.dbs.ledger.model.input;

import org.dbs.ledger.enums.TransactionType;

public record AccountBalanceUpdateInput(
        String fromAccountId,

        String toAccountId,

        TransactionType transactionType,

        Integer transferredAmount
) {
}
