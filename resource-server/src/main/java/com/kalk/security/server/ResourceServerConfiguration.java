package com.kalk.security.server;

import java.util.Optional;
import java.util.UUID;

import com.kalk.security.server.entity.Auditable;
import com.kalk.security.server.entity.UuidEntity;
import com.kalk.security.server.security.PermissionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
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
            return Optional.ofNullable(authenticatedUser.getName());
        };
    }

    @Bean
    AbstractMongoEventListener<Auditable> uuidGenerator(PermissionFactory permissionFactory) {
        return new AbstractMongoEventListener<>() {

            @Override
            public void onBeforeConvert(BeforeConvertEvent<Auditable> event) {
                super.onBeforeConvert(event);
                UuidEntity entity = event.getSource();
                if (entity.getId() == null) {
                    entity.setId(UUID.randomUUID());
                }
            }

            @Override
            public void onAfterSave(AfterSaveEvent<Auditable> event) {
                super.onAfterSave(event);
                permissionFactory.getHandler(event.getSource()).forEach(p -> p.create(event.getSource()));
            }
        };
    }
}
