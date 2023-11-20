package com.jwtauth.jwtauth.Security;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPointyPoint {

    public void commence(HttpRequest request, HttpResponse response) {

    }
}
