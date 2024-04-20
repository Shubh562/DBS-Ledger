package org.dbs.ledger.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dbs.ledger.dto.request.common.EmailRequest;
import org.dbs.ledger.dto.request.common.MobileRequest;
import org.dbs.ledger.enums.AccountType;
import org.dbs.ledger.enums.CurrencyName;
import org.dbs.ledger.validations.annotations.Password;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SignupRequest {
    @NotBlank
    private String name;

    @NotNull
    @URL(protocol = "https")
    private String profileUrl;

    @Valid
    @NotNull
    private EmailRequest email;

    @Valid
    @NotNull
    private MobileRequest mobile;

    @Password
    private String password;

    @NotNull
    private AccountType accountType;

    @NotNull
    private CurrencyName currencyName;
}
