package com.honey.reservation.service;

import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final CustomerRepository customerRepository;

    public void signUpCustomer(CustomerDto dto) {
        validateDuplicateLoginId(dto.loginId());

        customerRepository.save(dto.toEntity());
    }

    private void validateDuplicateLoginId(String loginId) {
        if (customerRepository.findLoginId(loginId).isPresent()) {
            throw new IllegalStateException("이미 가입된 회원 입니다");
        }
    }
}