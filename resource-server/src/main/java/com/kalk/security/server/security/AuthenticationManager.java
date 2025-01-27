package com.kalk.security.server.security;

import java.util.List;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager extends DefaultMethodSecurityExpressionHandler {

    public AuthenticationManager(CustomPermissionEvaluator... evaluators) {
        List.of(evaluators).forEach(super::setPermissionEvaluator);
    }
}
