package org.dbs.ledger.model;

import lombok.*;
import org.dbs.ledger.enums.AccountType;
import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.model.common.Email;
import org.dbs.ledger.model.common.Mobile;
import org.dbs.ledger.util.MongoConstants;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(MongoConstants.ACCOUNT_TABLE_NAME)
public class Account extends BaseEntity {
    private AccountType accountType;

    private String name;

    private String profileUrl;

    @Field(MongoConstants.EMAIL)
    private Email email;

    @Field(MongoConstants.MOBILE)
    private Mobile mobile;

    private String password;

    private CurrencyName currencyName;

    private Integer accountBalance;
}
