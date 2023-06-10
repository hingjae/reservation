package com.honey.reservation.controller;

import com.honey.reservation.dto.request.SignUpRequest;
import com.honey.reservation.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/customers")
@Controller
public class CustomerController {

    private final LoginService loginService;

    @GetMapping("/new")
    public String signUpForm() {
        return "customers/sign-up";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "customers/loginForm";
    }

    @PostMapping("/new")
    public String signUp(SignUpRequest request) {
        loginService.signUpCustomer(request.toDto());
        return "redirect:/login";
    }
}
