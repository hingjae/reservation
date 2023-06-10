package com.honey.reservation.dto.request;

import com.honey.reservation.dto.CustomerDto;

public record SignUpRequest(
        String loginId,
        String password,
        String name,
        String phoneNumber
) {
    public static SignUpRequest of(String loginId, String password, String name, String phoneNumber) {
        return new SignUpRequest(loginId, password, name, phoneNumber);
    }

    public CustomerDto toDto() {
        return CustomerDto.of(loginId, password, name, phoneNumber);
    }
}
