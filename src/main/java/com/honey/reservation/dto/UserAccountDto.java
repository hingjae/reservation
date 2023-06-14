package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;

public record UserAccountDto(
        String loginId, String password, String name, String phoneNumber
) {
    public static UserAccountDto of(String loginId, String password, String name, String phoneNumber) {
        return new UserAccountDto(loginId, password, name, phoneNumber);
    }

    public static UserAccountDto from(UserAccount userAccount) {
        return UserAccountDto.of(userAccount.getLoginId(), userAccount.getPassword(), userAccount.getName(), userAccount.getPhoneNumber());
    }

    public UserAccount toEntity() {
        return UserAccount.of(loginId, password, name, phoneNumber);
    }
}
