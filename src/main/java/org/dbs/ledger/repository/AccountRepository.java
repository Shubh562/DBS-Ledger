package org.dbs.ledger.repository;

import org.dbs.ledger.enums.Status;
import org.dbs.ledger.model.Account;
import org.dbs.ledger.model.common.Email;
import org.dbs.ledger.model.common.Mobile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findAccountByEmailAndStatus(Email email, Status status);

    Optional<Account> findAccountByMobileAndStatus(Mobile mobile, Status status);

    Optional<Account> findAccountByIdAndStatus(String id, Status status);
}
