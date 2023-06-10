package com.honey.reservation.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("로그인 컨트롤러")
@WebMvcTest
class CustomerControllerTest {

    @Autowired MockMvc mvc;
}