package org.dbs.ledger.model;

import lombok.*;
import org.dbs.ledger.enums.TransferType;
import org.dbs.ledger.util.MongoConstants;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(MongoConstants.ACCOUNT_ENTRY_TABLE_NAME)
public class AccountEntry extends BaseEntity {
    private String fromAccountId;

    private String toAccountId;

    private TransferType transferType;

    private Integer absoluteTransferredFunds;
}
