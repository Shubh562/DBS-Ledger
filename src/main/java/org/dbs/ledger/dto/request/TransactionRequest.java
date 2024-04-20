package org.dbs.ledger.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.enums.TransactionType;
import org.dbs.ledger.validations.annotations.ValidTransaction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ValidTransaction
public class TransactionRequest {
    private String toAccountId;

    @Min(1)
    private Integer amount;

    @NotNull
    private CurrencyName currencyName;

    @NotNull
    private TransactionType transactionType;
}
