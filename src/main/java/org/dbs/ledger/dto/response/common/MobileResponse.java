package org.dbs.ledger.dto.response.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MobileResponse {
    private String countryCode;

    private String mobileNumber;
}
