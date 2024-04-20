package org.dbs.ledger.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dbs.ledger.configuration.contexts.RequestContext;
import org.dbs.ledger.util.FilterOrderingConstants;
import org.dbs.ledger.util.MessageConstants;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(value = FilterOrderingConstants.HTTP_LOGGING_FILTER_ORDER)
@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {
    private final RequestContext requestContext;

    public HttpLoggingFilter(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            MDC.put(MessageConstants.REQUEST_ID, requestContext.getRequestId());
            filterChain.doFilter(request, response);
        } finally {
            log.info("Request to {}: {} - {}", request.getMethod(), formURI(request), response.getStatus());
            MDC.remove(MessageConstants.REQUEST_ID);
        }
    }

    private String formURI(HttpServletRequest request) {
        String queryString = request.getQueryString() == null ? "" : "?" + request.getQueryString();
        return request.getRequestURI() + queryString;
    }
}
