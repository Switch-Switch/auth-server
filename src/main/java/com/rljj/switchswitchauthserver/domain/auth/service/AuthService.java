package com.rljj.switchswitchauthserver.domain.auth.service;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginRequest loginRequest, HttpServletResponse response);
}
