package org.dbs.ledger.transformer;

import org.dbs.ledger.annotation.Transformer;
import org.dbs.ledger.enums.AccountEntryOutputStatus;
import org.dbs.ledger.enums.TransferType;
import org.dbs.ledger.model.AccountEntry;
import org.dbs.ledger.model.input.AccountEntryInput;
import org.dbs.ledger.model.output.AccountEntryOutput;
import org.dbs.ledger.util.TransactionUtil;

@Transformer
public class AccountEntryTransformer {
    public AccountEntry convertAccountEntryInputToModel(AccountEntryInput accountEntryInput, TransferType transferType) {
        return AccountEntry
                .builder()
                .fromAccountId(accountEntryInput.fromAccountId())
                .toAccountId(accountEntryInput.toAccountId())
                .absoluteTransferredFunds(accountEntryInput.transferredAmount())
                .transferType(transferType)
                .build();
    }

    public AccountEntry convertAccountEntryToContraEntry(AccountEntry accountEntry) {
        return AccountEntry
                .builder()
                .fromAccountId(accountEntry.getToAccountId())
                .toAccountId(accountEntry.getFromAccountId())
                .absoluteTransferredFunds(accountEntry.getAbsoluteTransferredFunds())
                .transferType(TransactionUtil.getContraTransferType(accountEntry.getTransferType()))
                .build();
    }

    public AccountEntryOutput convertModelToSuccessOutput(AccountEntry accountEntry) {
        return new AccountEntryOutput(accountEntry.getId(), AccountEntryOutputStatus.SUCCESS);
    }
}
