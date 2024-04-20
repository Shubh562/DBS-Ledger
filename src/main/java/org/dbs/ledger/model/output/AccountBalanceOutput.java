package org.dbs.ledger.model.output;

import org.dbs.ledger.enums.AccountBalanceOutputStatus;

public record AccountBalanceOutput(
        AccountBalanceOutputStatus accountBalanceOutputStatus,
        AccountBalance accountBalance
) {
    public static AccountBalanceOutput createFailedAccount(AccountBalanceOutputStatus accountBalanceOutputStatus) {
        return new AccountBalanceOutput(accountBalanceOutputStatus, null);
    }
}
