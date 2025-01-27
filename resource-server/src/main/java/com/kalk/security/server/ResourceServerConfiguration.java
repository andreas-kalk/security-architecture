package com.kalk.security.server;

import java.util.Optional;
import java.util.UUID;

import com.kalk.security.server.entity.UuidEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableMongoRepositories(basePackages = { "com.kalk.security.server.repo" })
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
public class ResourceServerConfiguration {

    @Bean
    AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
            return Optional.ofNullable(authenticatedUser.getPrincipal().toString());
        };
    }

    @Bean
    AbstractMongoEventListener<UuidEntity> uuidGenerator() {
        return new AbstractMongoEventListener<>() {

            @Override
            public void onBeforeConvert(BeforeConvertEvent<UuidEntity> event) {
                super.onBeforeConvert(event);
                UuidEntity entity = event.getSource();
                if (entity.getId() == null) {
                    entity.setId(UUID.randomUUID());
                }
            }
        };
    }
}
