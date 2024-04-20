package org.dbs.ledger.util;

import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.dbs.ledger.dto.response.wrapper.ResponseWrapper;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public final class CommonUtils {

    public static <T> void writeResponse(
            HttpServletResponse response,
            HttpStatus httpStatus,
            ResponseWrapper<T> responseWrapper,
            JsonMapper jsonMapper
    ) throws IOException {
        response.setHeader(MessageConstants.CONTENT_TYPE, MessageConstants.APPLICATION_JSON);
        response.setStatus(httpStatus.value());
        response.getWriter().println(jsonMapper.writeValueAsString(responseWrapper));
    }

    public static boolean isBearerToken(String token) {
        return token.startsWith(MessageConstants.BEARER_SPACE);
    }

    private CommonUtils() {}
}
