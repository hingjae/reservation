package com.honey.reservation.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalCatcher {

    @ExceptionHandler(Exception.class)
    public String exception(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/errorPage";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/errorPage";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/errorPage";
    }
}
