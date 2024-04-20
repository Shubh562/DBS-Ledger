package org.dbs.ledger.dto.request.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dbs.ledger.dto.request.common.EmailRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public final class EmailSignInRequest {
    @Valid
    @NotNull
    private EmailRequest emailRequest;

    @NotBlank
    private String password;
}
