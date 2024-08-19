package com.rljj.switchswitchauthserver.domain.auth.controller;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchauthserver.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        authService.login(loginRequest, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
