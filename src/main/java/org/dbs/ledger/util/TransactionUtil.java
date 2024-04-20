package org.dbs.ledger.util;

import org.dbs.ledger.enums.TransferType;

public final class TransactionUtil {
    public static TransferType getContraTransferType(TransferType transferType) {
        return switch (transferType) {
            case DEBIT -> TransferType.CREDIT;
            case CREDIT -> TransferType.DEBIT;
        };
    }

    private TransactionUtil() {}
}
