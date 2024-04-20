package org.dbs.ledger.helper;

import org.dbs.ledger.model.AccountEntry;
import org.dbs.ledger.model.input.AccountEntryInput;
import org.dbs.ledger.model.output.AccountEntryOutput;

import java.util.List;

public interface AccountEntryHelper {
    AccountEntryOutput createAccountEntry(AccountEntryInput accountEntryInput);

    List<AccountEntry> getAllAccountEntriesForAccountId(String accountId);
}
