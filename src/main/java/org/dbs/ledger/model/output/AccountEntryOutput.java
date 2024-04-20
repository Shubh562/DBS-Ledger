package org.dbs.ledger.model.output;

import org.dbs.ledger.enums.AccountEntryOutputStatus;

public record AccountEntryOutput(
        String accountEntryId,

        AccountEntryOutputStatus accountEntryOutputStatus
) {
    public static AccountEntryOutput createFailedEntryAccountOutput() {
        return new AccountEntryOutput(null, AccountEntryOutputStatus.FAILED);
    }
}
