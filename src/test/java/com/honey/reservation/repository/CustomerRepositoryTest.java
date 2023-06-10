package com.honey.reservation.repository;

import com.honey.reservation.domain.Customer;
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
@DisplayName("CustomerRepository 테스트")
@Import(CustomerRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired CustomerRepository customerRepository;

    @Commit
    @Test
    void crud() {
        Customer customer = Customer.of("loginId", "pw", "honey", null);
        customerRepository.save(customer);
        Customer findCustomer = customerRepository.findById(customer.getLoginId()).get();

        assertThat(customer).isEqualTo(findCustomer);
        findCustomer.setName("newName");
        customerRepository.flush();

        customerRepository.delete(customer);
    }

    @DisplayName("로그인 아이디 DB에 존재 O")
    @Test
    void givenExistingLoginId_whenSelectLoginId_thenResultIsNotEmpty() {
        customerRepository.save(Customer.of("loginId", "password", "name", "phoneNumber"));
        customerRepository.flush();
        Optional<String> findLoginId = customerRepository.findLoginId("loginId");
        assertThat(findLoginId).isNotEmpty();
    }

    @DisplayName("로그인 아이디 DB에 존재 X")
    @Test
    void givenNotExistingLoginId_whenSelectLoginId_thenResultIsEmpty() {
        customerRepository.save(Customer.of("loginId", "password", "name", "phoneNumber"));
        customerRepository.flush();
        Optional<String> findLoginId = customerRepository.findLoginId("notExistLoginId");
        assertThat(findLoginId).isEmpty();
    }

    @DisplayName("로그인 아이디로 Customer 찾기")
    @Test
    void givenLoginId_whenFindByLoginId_thenReturnOptionalCustomer() {
        customerRepository.save(Customer.of("loginId", "password", "name", "phoneNumber"));
        Optional<Customer> findCustomer = customerRepository.findById("loginId");
        assertThat(findCustomer).isNotEmpty();
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