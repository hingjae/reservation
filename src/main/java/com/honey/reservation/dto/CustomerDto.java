package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;

public record CustomerDto(
        String loginId, String password, String name, String phoneNumber
) {
    public static CustomerDto of(String loginId, String password, String name, String phoneNumber) {
        return new CustomerDto(loginId, password, name, phoneNumber);
    }

    public static CustomerDto from(UserAccount customer) {
        return CustomerDto.of(customer.getLoginId(), customer.getPassword(), customer.getName(), customer.getPhoneNumber());
    }

    public UserAccount toEntity() {
        return UserAccount.of(loginId, password, name, phoneNumber);
    }
}
