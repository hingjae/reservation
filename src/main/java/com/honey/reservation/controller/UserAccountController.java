package com.honey.reservation.controller;

import com.honey.reservation.dto.request.SignUpRequest;
import com.honey.reservation.dto.security.UserAccountUserDetails;
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

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserAccountController {

    private final LoginService loginService;

    @GetMapping("/sign-up")
    public String signUpForm(ModelMap map) {
        map.addAttribute("userAccount", SignUpRequest.of());
        return "users/sign-up";
    }

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, ModelMap map) {
        String error = request.getParameter("error");
        if (error != null) {
            map.addAttribute("errorMessage", "아이디와 비밀번호가 일치하지 않습니다.");
        }
        return "users/login";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("userAccount") SignUpRequest request, BindingResult bindingResult, ModelMap map) {
        try {
            loginService.signUpUserAccount(UserAccountUserDetails.from(request.toDto()));
        } catch (IllegalStateException e) {
            map.addAttribute("errorMessage", "이메일이 이미 존재합니다.");
            return "users/sign-up";
        }

        return "redirect:/";
    }
}
