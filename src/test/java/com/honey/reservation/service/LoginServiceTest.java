package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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
        CustomerDto dto = CustomerDto.of(null, "loginId", "password", "name", "01012345678");
        Customer customer = Customer.of(1L, "loginId", "password", "name", "01012345678");

        loginService.signUpCustomer(dto);

        Optional<Customer> findMember = customerRepository.findById(1L);

        assertThat(findMember).isNotEmpty();
        assertThat(customer.getId()).isEqualTo(findMember.get().getId());
        assertThat(customer.getLoginId()).isEqualTo(findMember.get().getLoginId());
    }

    @DisplayName("회원가입 실패")
    @Test
    void givenExistingCustomer_whenSignUp_thenFailedSave() {
        customerRepository.save(Customer.of(1L, "existingLoginId", "password", "name", "01012345678"));
        CustomerDto dto = CustomerDto.of(null, "existingLoginId", "password", "name", "01012345678");

        assertThatThrownBy(() -> loginService.signUpCustomer(dto)).isInstanceOf(IllegalStateException.class);
    }
}