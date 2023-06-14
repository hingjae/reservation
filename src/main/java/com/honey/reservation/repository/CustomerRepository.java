package com.honey.reservation.repository;

import com.honey.reservation.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<UserAccount, String> {
    @Query("select c.loginId from UserAccount c where c.loginId = :loginId")
    Optional<String> findLoginId(@Param("loginId") String loginId);
}
