package org.dbs.ledger.repository;

import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.enums.Status;
import org.dbs.ledger.model.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends MongoRepository<Currency, String> {
    Optional<Currency> findCurrencyByCurrencyNameAndStatus(CurrencyName currencyName, Status status);
}
