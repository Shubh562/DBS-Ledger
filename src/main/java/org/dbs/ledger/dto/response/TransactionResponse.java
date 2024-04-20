package org.dbs.ledger.dto.response;

import lombok.*;
import org.dbs.ledger.enums.TransactionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TransactionResponse {
    private String transactionId;

    private TransactionType transactionType;

    private String accountId;

    private Integer availableBalance;
}
