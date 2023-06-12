package com.honey.reservation.service;

import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.security.CustomerUserDetails;
import com.honey.reservation.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUpCustomer(CustomerUserDetails dto) {
        validateDuplicateLoginId(dto.username()); //username == loginId
        customerRepository.save(dto.toEntity(passwordEncoder));
    }

    @Transactional(readOnly = true)
    public Optional<CustomerDto> loadUserByUsername(String username) {
        return customerRepository.findById(username)
                .map(CustomerDto::from);
    }

    private void validateDuplicateLoginId(String loginId) {
        if (customerRepository.findLoginId(loginId).isPresent()) {
            throw new IllegalStateException("이미 가입된 회원 입니다");
        }
    }
}
