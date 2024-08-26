package com.rljj.switchswitchauthserver.domain.auth.service;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchauthserver.domain.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String login(LoginRequest loginRequest, HttpServletResponse response);

    String signup(SignupRequest signupRequest, HttpServletResponse response);
}
