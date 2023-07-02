package com.honey.reservation.dto.api;

import com.honey.reservation.domain.UserAccount;

public record UserAccountDto(
        String loginId, String name, String phoneNumber
) {
    public static UserAccountDto of(String loginId, String name, String phoneNumber) {
        return new UserAccountDto(loginId, name, phoneNumber);
    }

    public static UserAccountDto from(UserAccount userAccount) {
        return UserAccountDto.of(userAccount.getLoginId(), userAccount.getName(), userAccount.getPhoneNumber());
    }
}
