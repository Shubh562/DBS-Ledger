package managers.jwt.configurations;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JwtManagerConfiguration {
    private Long tokenValidityDurationMillis;

    private String tokenIssuer;

    private String rsaPrivateKey;

    private String rsaPublicKey;
}
