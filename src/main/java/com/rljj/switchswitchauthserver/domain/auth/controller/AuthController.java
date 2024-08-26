package com.rljj.switchswitchauthserver.domain.auth.controller;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchauthserver.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchauthserver.domain.auth.service.AuthService;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return new ResponseEntity<>(authService.login(loginRequest, response), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest, HttpServletResponse response) {
        return new ResponseEntity<>(authService.signup(signupRequest, response), HttpStatus.OK);
    }

    @GetMapping("/refresh/{jwt}")
    public ResponseEntity<String> refresh(@PathVariable("jwt") String jwt, HttpServletResponse response) {
        return new ResponseEntity<>(jwtProvider.refreshAuthorization(jwt, response), HttpStatus.OK);
    }
}
