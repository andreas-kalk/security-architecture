package com.kalk.security.server;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    private final OAuth2ResourceServerProperties props;

    public SecurityConfiguration(OAuth2ResourceServerProperties props) {
        this.props = props;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(token -> token.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setPrincipalClaimName(props.getJwt().getPrincipalClaimName());

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            Map<String, Object> resourceAccess = jwt.getClaimAsMap("realm_access");
            if (resourceAccess.get("roles") instanceof List<?> list) {
                List<String> roles = list.stream().map(Object::toString).toList();
                return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }
            return List.of();
        });
        return jwtAuthenticationConverter;
    }
}
