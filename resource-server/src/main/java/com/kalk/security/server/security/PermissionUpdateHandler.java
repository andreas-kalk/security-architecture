package com.kalk.security.server.security;

public interface PermissionUpdateHandler {

    void create(Object entity);

    boolean canHandle(Object object);
}
