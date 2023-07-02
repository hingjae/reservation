package com.honey.reservation.repository;

import com.honey.reservation.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Query("select u.loginId from UserAccount u where u.loginId = :loginId")
    Optional<String> findLoginId(@Param("loginId") String loginId);

    @Query("select u.name from UserAccount u where u.loginId = :loginId")
    Optional<String> findNameByLoginId(@Param("loginId") String loginId);
}
