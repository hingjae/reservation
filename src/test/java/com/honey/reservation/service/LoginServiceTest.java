package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("서비스 로직 - 회원가입")
@Transactional
@SpringBootTest
class LoginServiceTest {

    @Autowired LoginService loginService;

    @Autowired
    UserAccountRepository userAccountRepository;

    @DisplayName("회원가입 성공")
    @Test
    void givenCorrectUserAccountDto_whenSignUp_thenSaveUserAccount() {
        UserAccountDto dto = UserAccountDto.of("loginId", "password", "name", "01012345678");

        loginService.signUpUserAccount(UserAccountUserDetails.from(dto));
        Optional<UserAccount> findUserAccount = userAccountRepository.findById("loginId");

        assertThat(findUserAccount).isNotEmpty();
    }

    @DisplayName("회원가입 실패")
    @Test
    void givenExistingUserAccount_whenSignUp_thenFailedSave() {
        userAccountRepository.save(UserAccount.of("existingLoginId", "password", "name", "01012345678"));
        UserAccountDto dto = UserAccountDto.of("existingLoginId", "password", "name", "01012345678");

        assertThatThrownBy(() -> loginService.signUpUserAccount(UserAccountUserDetails.from(dto))).isInstanceOf(IllegalStateException.class);
    }

}