package org.dbs.ledger.repository;

import org.dbs.ledger.enums.Status;
import org.dbs.ledger.model.AccountEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountEntryRepository extends MongoRepository<AccountEntry, String> {
    List<AccountEntry> findAccountEntriesByFromAccountIdAndStatus(String fromAccountId, Status status);
}
