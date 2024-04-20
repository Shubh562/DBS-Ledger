package org.dbs.ledger.dto.request.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class EmailRequest {
    @NotNull
    @Email
    private String emailId;
}
