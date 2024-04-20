package org.dbs.ledger.security.handlers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.jwt.exceptions.JwtManagerException;
import managers.jwt.impl.JwtAccessTokenManager;
import org.dbs.ledger.dto.response.wrapper.ErrorResponse;
import org.dbs.ledger.dto.response.wrapper.ResponseWrapper;
import org.dbs.ledger.enums.ErrorCode;
import org.dbs.ledger.util.CommonUtils;
import org.dbs.ledger.util.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final JsonMapper jsonMapper;

    private final JwtAccessTokenManager jwtAuthenticationManager;

    public AuthenticationExceptionHandler(
            JsonMapper jsonMapper,
            JwtAccessTokenManager jwtAuthenticationManager
    ) {
        this.jsonMapper = jsonMapper;
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        String authorizationHeader = request.getHeader(MessageConstants.AUTHORIZATION);
        if (authorizationHeader == null) {
            ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
            errorResponse.setDetail(MessageConstants.AUTHORIZATION_HEADER_MUST_BE_PRESENT);
            CommonUtils.writeResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    ResponseWrapper.failure(List.of(errorResponse)),
                    jsonMapper
            );
            return;
        }

        if (!CommonUtils.isBearerToken(authorizationHeader)) {
            ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.TOKEN_MUST_START_WITH_BEARER);
            CommonUtils.writeResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    ResponseWrapper.failure(List.of(errorResponse)),
                    jsonMapper
            );
            return;
        }

        try {
            jwtAuthenticationManager.verifyAndDecodeToken(authorizationHeader);
        } catch (JwtManagerException e) {
            ErrorCode errorCode = switch (e.getErrorCause()) {
                case JWT_TOKEN_DECODING_FAILED -> ErrorCode.INVALID_JWT_TOKEN;
                case JWT_TOKEN_EXPIRED -> ErrorCode.JWT_TOKEN_EXPIRED;
            };
            ErrorResponse errorResponse = ErrorResponse.from(errorCode);
            CommonUtils.writeResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    ResponseWrapper.failure(List.of(errorResponse)),
                    jsonMapper
            );
        }
    }
}
