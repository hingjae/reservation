package com.honey.reservation.repository;

import com.honey.reservation.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("UserAccountRepository 테스트")
@Import(UserAccountRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Commit
    @Test
    void crud() {
        UserAccount userAccount = UserAccount.of("loginId", "pw", "honey", null);
        userAccountRepository.save(userAccount);
        UserAccount findUserAccount = userAccountRepository.findById(userAccount.getLoginId()).get();

        assertThat(userAccount).isEqualTo(findUserAccount);
        findUserAccount.setName("newName");
        userAccountRepository.flush();

        userAccountRepository.delete(userAccount);
    }

    @DisplayName("로그인 아이디 DB에 존재 O")
    @Test
    void givenExistingLoginId_whenSelectLoginId_thenResultIsNotEmpty() {
        userAccountRepository.save(UserAccount.of("loginId", "password", "name", "phoneNumber"));
        userAccountRepository.flush();
        Optional<String> findLoginId = userAccountRepository.findLoginId("loginId");
        assertThat(findLoginId).isNotEmpty();
    }

    @DisplayName("로그인 아이디 DB에 존재 X")
    @Test
    void givenNotExistingLoginId_whenSelectLoginId_thenResultIsEmpty() {
        userAccountRepository.save(UserAccount.of("loginId", "password", "name", "phoneNumber"));
        userAccountRepository.flush();
        Optional<String> findLoginId = userAccountRepository.findLoginId("notExistLoginId");
        assertThat(findLoginId).isEmpty();
    }

    @DisplayName("로그인 아이디로 UserAccount 찾기")
    @Test
    void givenLoginId_whenFindByLoginId_thenReturnOptionalUserAccount() {
        userAccountRepository.save(UserAccount.of("loginId", "password", "name", "phoneNumber"));
        Optional<UserAccount> findUserAccount = userAccountRepository.findById("loginId");
        assertThat(findUserAccount).isNotEmpty();
    }

    @EnableJpaAuditing
    @TestConfiguration
    static class TestJpaConfig {
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("sample");
        }
    }
}