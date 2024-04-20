package org.dbs.ledger.helper.impl;

import org.dbs.ledger.annotation.Helper;
import org.dbs.ledger.enums.Status;
import org.dbs.ledger.enums.TransactionType;
import org.dbs.ledger.enums.TransferType;
import org.dbs.ledger.helper.AccountEntryHelper;
import org.dbs.ledger.helper.IdHelper;
import org.dbs.ledger.model.AccountEntry;
import org.dbs.ledger.model.input.AccountEntryInput;
import org.dbs.ledger.model.output.AccountEntryOutput;
import org.dbs.ledger.repository.AccountEntryRepository;
import org.dbs.ledger.transformer.AccountEntryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

@Helper
public class AccountEntryHelperImpl implements AccountEntryHelper {
    private final AccountEntryRepository accountEntryRepository;

    private final AccountEntryTransformer accountEntryTransformer;

    private final IdHelper idHelper;

    @Autowired
    public AccountEntryHelperImpl(AccountEntryRepository accountEntryRepository, AccountEntryTransformer accountEntryTransformer, IdHelper idHelper) {
        this.accountEntryRepository = accountEntryRepository;
        this.accountEntryTransformer = accountEntryTransformer;
        this.idHelper = idHelper;
    }

    @Override
    public AccountEntryOutput createAccountEntry(AccountEntryInput accountEntryInput) {
        AccountEntry accountEntry = accountEntryTransformer.convertAccountEntryInputToModel(accountEntryInput, TransferType.CREDIT);
        accountEntry.setId(idHelper.getNextId());
        accountEntry.setStatus(Status.ACTIVE);

        List<AccountEntry> accountEntries = new ArrayList<>();
        accountEntries.add(accountEntry);

        if (accountEntryInput.transactionType().equals(TransactionType.TRANSFER)) {
            AccountEntry contraAccountEntry = accountEntryTransformer.convertAccountEntryToContraEntry(accountEntry);
            accountEntries.add(contraAccountEntry);
        }
        try {
            accountEntryRepository.saveAll(accountEntries);
            return accountEntryTransformer.convertModelToSuccessOutput(accountEntry);
        } catch (DuplicateKeyException duplicateKeyException) {
            return AccountEntryOutput.createFailedEntryAccountOutput();
        }
    }

    @Override
    public List<AccountEntry> getAllAccountEntriesForAccountId(String accountId) {
        return accountEntryRepository.findAccountEntriesByFromAccountIdAndStatus(accountId, Status.ACTIVE);
    }
}
