package managers.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import managers.jwt.AccessTokenAlgorithm;
import managers.jwt.AccessTokenManager;
import managers.jwt.configurations.JwtManagerConfiguration;
import managers.jwt.enums.JwtErrorCause;
import managers.jwt.exceptions.JwtManagerException;
import managers.jwt.models.JwtPayload;
import managers.jwt.models.JwtToken;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtAccessTokenManager implements AccessTokenManager<JwtPayload, JwtToken, String> {
    private final String tokenIssuer;

    private final Long tokenValidityDurationMillis;

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    public JwtAccessTokenManager(
            AccessTokenAlgorithm accessTokenAlgorithm,
            JwtManagerConfiguration jwtManagerConfiguration
    ) {
        this.algorithm = accessTokenAlgorithm.getAlgorithm();
        this.tokenIssuer = jwtManagerConfiguration.getTokenIssuer();
        this.tokenValidityDurationMillis = jwtManagerConfiguration.getTokenValidityDurationMillis();
        this.jwtVerifier = JWT.require(algorithm).withIssuer(tokenIssuer).build();
    }

    @Override
    public JwtPayload verifyAndDecodeToken(String token) throws JwtManagerException {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return convertDecodedJwtTokenToJwtPayload(decodedJWT);
        } catch (TokenExpiredException tokenExpiredException) {
            throw new JwtManagerException(JwtErrorCause.JWT_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new JwtManagerException(JwtErrorCause.JWT_TOKEN_DECODING_FAILED);
        }
    }

    @Override
    public JwtToken createToken(JwtPayload payload) {
        long currentTimestamp = Instant.now().toEpochMilli();
        long expiresAtTimestamp = currentTimestamp + tokenValidityDurationMillis;
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuer(tokenIssuer)
                .withSubject(payload.getSub())
                .withIssuedAt(new Date(currentTimestamp))
                .withExpiresAt(new Date(expiresAtTimestamp))
                .withJWTId(UUID.randomUUID().toString());
        String token = jwtBuilder.sign(algorithm);
        return JwtToken.builder().token(token).expiresAt(expiresAtTimestamp).build();
    }

    private JwtPayload convertDecodedJwtTokenToJwtPayload(DecodedJWT decodedJWT) {
        String sub = decodedJWT.getSubject();
        return new JwtPayload(sub);
    }
}
