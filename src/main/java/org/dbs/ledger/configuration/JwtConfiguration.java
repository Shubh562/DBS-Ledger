package org.dbs.ledger.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dbs.jwt.configuration")
@Getter
@Setter
@NoArgsConstructor
public class JwtConfiguration {
    private Long tokenValidityDurationMillis;

    private String tokenIssuer;

    private String rsaPrivateKey;

    private String rsaPublicKey;
}
