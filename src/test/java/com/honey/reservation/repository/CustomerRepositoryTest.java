package com.honey.reservation.repository;

import com.honey.reservation.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired CustomerRepository customerRepository;

    @Commit
    @Test
    void crud() {
        Customer customer = Customer.of("loginId", "pw", "honey", null);
        customerRepository.save(customer);
        Customer findCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(customer).isEqualTo(findCustomer);
        findCustomer.setName("newName");
        customerRepository.flush();

        customerRepository.delete(customer);

    }
}