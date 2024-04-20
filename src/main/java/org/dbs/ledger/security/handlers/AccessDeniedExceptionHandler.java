package org.dbs.ledger.security.handlers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dbs.ledger.dto.response.wrapper.ResponseWrapper;
import org.dbs.ledger.util.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
    private final JsonMapper jsonMapper;

    public AccessDeniedExceptionHandler(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        CommonUtils.writeResponse(response, HttpStatus.FORBIDDEN, ResponseWrapper.failure(null), jsonMapper);
    }
}
