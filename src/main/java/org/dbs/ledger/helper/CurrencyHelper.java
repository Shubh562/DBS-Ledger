package org.dbs.ledger.helper;

import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.model.Currency;

import java.util.Optional;

public interface CurrencyHelper {
    Optional<Currency> getCurrencyByName(CurrencyName currencyName);
}
