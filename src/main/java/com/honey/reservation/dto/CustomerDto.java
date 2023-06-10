package com.honey.reservation.dto;

import com.honey.reservation.domain.Customer;

public record CustomerDto(
        String loginId, String password, String name, String phoneNumber
) {
    public static CustomerDto of(String loginId, String password, String name, String phoneNumber) {
        return new CustomerDto(loginId, password, name, phoneNumber);
    }

    public static CustomerDto from(Customer customer) {
        return CustomerDto.of(customer.getLoginId(), customer.getPassword(), customer.getName(), customer.getPhoneNumber());
    }

    public Customer toEntity() {
        return Customer.of(loginId, password, name, phoneNumber);
    }
}
