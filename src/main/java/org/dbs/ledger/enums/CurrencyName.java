package org.dbs.ledger.enums;

import lombok.Getter;

@Getter
public enum CurrencyName {
    INDIAN_RUPEE(CurrencyCode.INR),

    AMERICAN_DOLLAR(CurrencyCode.DOLLAR),

    EURO(CurrencyCode.EURO),
    ;

    private final CurrencyCode currencyCode;

    CurrencyName(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
