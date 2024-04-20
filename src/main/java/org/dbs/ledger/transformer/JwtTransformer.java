package org.dbs.ledger.transformer;

import managers.jwt.configurations.JwtManagerConfiguration;
import org.dbs.ledger.annotation.Transformer;
import org.dbs.ledger.configuration.JwtConfiguration;

@Transformer
public class JwtTransformer {

    public JwtManagerConfiguration convertJwtConfigurationToJwtManagerConfiguration(JwtConfiguration jwtConfiguration) {
        return JwtManagerConfiguration.builder()
                .tokenValidityDurationMillis(jwtConfiguration.getTokenValidityDurationMillis())
                .tokenIssuer(jwtConfiguration.getTokenIssuer())
                .rsaPrivateKey(jwtConfiguration.getRsaPrivateKey())
                .rsaPublicKey(jwtConfiguration.getRsaPublicKey())
                .build();
    }
}