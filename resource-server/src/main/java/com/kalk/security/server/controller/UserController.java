package com.kalk.security.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @GetMapping(path = "/me")
    @PreAuthorize("isAuthenticated()")
    public String me() {
        return "Me called";
    }
}
