package org.dbs.ledger.security.configurations;

import org.dbs.ledger.security.filter.JwtFilter;
import org.dbs.ledger.security.handlers.AccessDeniedExceptionHandler;
import org.dbs.ledger.security.handlers.AuthenticationExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtFilter jwtFilter;

    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;

    private final List<String> defaultPermittedRoutes;

    public WebSecurityConfig(
            JwtFilter jwtFilter,
            AuthenticationExceptionHandler authenticationExceptionHandler,
            AccessDeniedExceptionHandler accessDeniedExceptionHandler,
            @Value("${dbs.jwt.configuration.permittedRoutes:}") List<String> defaultPermittedRoutes
    ) {
        this.jwtFilter = jwtFilter;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
        this.accessDeniedExceptionHandler = accessDeniedExceptionHandler;
        this.defaultPermittedRoutes = defaultPermittedRoutes;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        List<String> permittedRoutes = List.of(
                "/accounts/sign-up",
                "/accounts/mobile/sign-in",
                "/accounts/email/sign-in"
        );
        String[] finalPermittedRoutes = Stream.concat(defaultPermittedRoutes.stream(), permittedRoutes.stream())
                .toArray(String[]::new);

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        configurer -> configurer.requestMatchers(finalPermittedRoutes)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        configurer -> configurer.authenticationEntryPoint(authenticationExceptionHandler)
                                .accessDeniedHandler(accessDeniedExceptionHandler)
                );

        return httpSecurity.build();
    }
}
