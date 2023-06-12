package com.honey.reservation.controller;

import com.honey.reservation.dto.request.SignUpRequest;
import com.honey.reservation.dto.security.CustomerUserDetails;
import com.honey.reservation.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customers")
@Controller
public class CustomerController {

    private final LoginService loginService;

    @GetMapping("/sign-up")
    public String signUpForm(ModelMap map) {
        map.addAttribute("customer", SignUpRequest.of());
        return "customers/sign-up";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "customers/login";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("customer") SignUpRequest request, BindingResult bindingResult, ModelMap map) {
        try {
            loginService.signUpCustomer(CustomerUserDetails.from(request.toDto()));
        } catch (IllegalStateException e) {
            map.addAttribute("errorMessage", "이메일이 이미 존재합니다.");
            return "customers/sign-up";
        }

        return "redirect:/";
    }
}
