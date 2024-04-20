package org.dbs.ledger.model;

import lombok.*;
import org.dbs.ledger.enums.CurrencyCode;
import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.util.MongoConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(MongoConstants.CURRENCY_TABLE_NAME)
public class Currency extends BaseEntity {
    @Indexed(unique = true)
    private CurrencyName currencyName;

    private CurrencyCode currencyCode;

    private Integer decimalPlaces;
}
