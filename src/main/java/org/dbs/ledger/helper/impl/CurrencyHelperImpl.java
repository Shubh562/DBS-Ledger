package org.dbs.ledger.helper.impl;

import org.dbs.ledger.annotation.Helper;
import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.enums.Status;
import org.dbs.ledger.helper.CurrencyHelper;
import org.dbs.ledger.model.Currency;
import org.dbs.ledger.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Helper
public class CurrencyHelperImpl implements CurrencyHelper {
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyHelperImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Optional<Currency> getCurrencyByName(CurrencyName currencyName) {
        return currencyRepository.findCurrencyByCurrencyNameAndStatus(currencyName, Status.ACTIVE);
    }
}
