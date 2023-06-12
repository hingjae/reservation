package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.security.CustomerUserDetails;
import com.honey.reservation.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("서비스 로직 - 회원가입")
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    CustomerRepository customerRepository;

    @DisplayName("회원가입 성공")
    @Test
    void givenCorrectCustomerDto_whenSignUp_thenSaveCustomer() {
        CustomerDto dto = CustomerDto.of("loginId", "password", "name", "01012345678");

        loginService.signUpCustomer(CustomerUserDetails.from(dto));

        Optional<Customer> findCustomer = customerRepository.findById("loginId");

        assertThat(findCustomer).isNotEmpty();
    }

    @DisplayName("회원가입 실패")
    @Test
    void givenExistingCustomer_whenSignUp_thenFailedSave() {
        customerRepository.save(Customer.of("existingLoginId", "password", "name", "01012345678"));
        CustomerDto dto = CustomerDto.of("existingLoginId", "password", "name", "01012345678");

        assertThatThrownBy(() -> loginService.signUpCustomer(CustomerUserDetails.from(dto))).isInstanceOf(IllegalStateException.class);
    }

}