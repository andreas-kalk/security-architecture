package com.kalk.security.server.security;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public final class PermissionFactory {

    private final List<PermissionUpdateHandler> updateHandlers;

    public PermissionFactory(List<PermissionUpdateHandler> handlers) {
        this.updateHandlers = handlers;
    }

    public List<PermissionUpdateHandler> getHandler(Object o) {
        return updateHandlers.stream().filter(p -> p.canHandle(o)).toList();
    }
}
