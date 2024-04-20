package org.dbs.ledger.model.output;

public record AccountBalance(
        String accountId,

        Integer availableBalance
) {
}
