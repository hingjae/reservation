package com.honey.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "redirect:/reservations/search-date";
    }

    @GetMapping("/go")
    public String go() {
        return "go";
    }
}
