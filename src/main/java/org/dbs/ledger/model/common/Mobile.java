package org.dbs.ledger.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Mobile {
    private String countryCode;

    private String mobileNumber;
}
