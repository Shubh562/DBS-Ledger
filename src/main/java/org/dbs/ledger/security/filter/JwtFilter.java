package org.dbs.ledger.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import managers.jwt.impl.JwtAccessTokenManager;
import managers.jwt.models.JwtPayload;
import org.dbs.ledger.configuration.contexts.AccountContext;
import org.dbs.ledger.util.CommonUtils;
import org.dbs.ledger.util.MessageConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtAccessTokenManager jwtAuthenticationManager;

    private final AccountContext accountContext;

    public JwtFilter(
            JwtAccessTokenManager jwtAuthenticationManager,
            AccountContext accountContext
    ) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.accountContext = accountContext;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(MessageConstants.AUTHORIZATION);
            if (authorizationHeader != null && (CommonUtils.isBearerToken(authorizationHeader))) {
                String token = authorizationHeader.substring(MessageConstants.BEARER_SPACE.length());
                JwtPayload jwtPayload = jwtAuthenticationManager.verifyAndDecodeToken(token);
                setupAccountContext(accountContext, jwtPayload, token);
                List<SimpleGrantedAuthority> grantedAuthorities = List.of();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        jwtPayload.getSub(), null, grantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception ignored) {
            // Ignore Exception
        }
        filterChain.doFilter(request, response);
    }

    private void setupAccountContext(AccountContext accountContext, JwtPayload jwtPayload, String token) {
        accountContext.setToken(token);
        accountContext.setAccountId(jwtPayload.getSub());
    }

}
