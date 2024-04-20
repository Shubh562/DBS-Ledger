package org.dbs.ledger.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.mongock.runner.springboot.EnableMongock;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import managers.jwt.AccessTokenAlgorithm;
import managers.jwt.configurations.JwtManagerConfiguration;
import managers.jwt.impl.JwtAccessTokenManager;
import managers.jwt.impl.RsaAccessTokenAlgorithmImpl;
import org.dbs.ledger.configuration.contexts.RequestContext;
import org.dbs.ledger.configuration.contexts.AccountContext;
import org.dbs.ledger.database.migrations.Currency001Migration;
import org.dbs.ledger.transformer.JwtTransformer;
import org.dbs.ledger.util.MessageConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import java.util.Currency;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableMongoAuditing
public class ApplicationConfiguration {
    @Bean
    @Primary
    public JsonMapper getJsonMapper() {
        return JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .findAndAddModules()
                .build();
    }

    @Bean
    public List<Currency> currencies(Currency001Migration currency001Migration) {
        currency001Migration.executeMigration();
        return List.of();
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public RequestContext getRequestContext() {
        return new RequestContext();
    }

    @Bean
    public AccountContext getAccountContext() {
        return new AccountContext();
    }

    @Bean
    public JwtAccessTokenManager getJwtTokenManager(JwtConfiguration jwtConfiguration, JwtTransformer jwtTransformer) {
        AccessTokenAlgorithm accessTokenAlgorithm = new RsaAccessTokenAlgorithmImpl(
                jwtConfiguration.getRsaPublicKey(),
                jwtConfiguration.getRsaPrivateKey()
        );
        JwtManagerConfiguration jwtManagerConfiguration = jwtTransformer.convertJwtConfigurationToJwtManagerConfiguration(jwtConfiguration);
        return new JwtAccessTokenManager(accessTokenAlgorithm, jwtManagerConfiguration);
    }

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        Server server = new Server().url(servletContext.getContextPath());
        return new OpenAPI()
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList(MessageConstants.BEARER_AUTH))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        MessageConstants.BEARER_AUTH,
                                        new SecurityScheme()
                                                .name(MessageConstants.BEARER_AUTH)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
