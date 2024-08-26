package com.rljj.switchswitchauthserver.domain.member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }
}
