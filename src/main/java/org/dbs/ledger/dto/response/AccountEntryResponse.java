package org.dbs.ledger.dto.response;

import lombok.*;
import org.dbs.ledger.enums.TransferType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AccountEntryResponse {
    private String toAccountId;

    private TransferType transferType;

    private Double transferredAmount;
}
