package org.dbs.ledger.dto.request.common;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dbs.ledger.validations.annotations.CountryCode;
import org.dbs.ledger.validations.annotations.MobileNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MobileRequest {
    @NotNull
    @CountryCode
    private String countryCode;

    @NotNull
    @MobileNumber
    private String mobileNumber;
}
