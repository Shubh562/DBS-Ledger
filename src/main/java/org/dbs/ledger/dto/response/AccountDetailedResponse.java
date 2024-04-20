package org.dbs.ledger.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AccountDetailedResponse {
    private String accountId;

    private String name;

    private Double accountBalance;

    private List<AccountEntryResponse> accountEntries;
}
