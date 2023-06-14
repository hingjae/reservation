package com.honey.reservation.service;

import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.repository.UserAccountRepository;
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

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUpUserAccount(UserAccountUserDetails dto) {
        validateDuplicateLoginId(dto.username()); //username == loginId
        userAccountRepository.save(dto.toEntity(passwordEncoder));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> loadUserByUsername(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

    private void validateDuplicateLoginId(String loginId) {
        if (userAccountRepository.findLoginId(loginId).isPresent()) {
            throw new IllegalStateException("이미 가입된 회원 입니다");
        }
    }
}
