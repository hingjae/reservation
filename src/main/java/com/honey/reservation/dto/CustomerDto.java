package com.honey.reservation.dto;

import com.honey.reservation.domain.Customer;

public record CustomerDto(
        Long id, String name, String phoneNumber
) {
    public static CustomerDto of(Long id, String name, String phoneNumber) {
        return new CustomerDto(id, name, phoneNumber);
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(
                customer.getId(), customer.getName(), customer.getPhoneNumber()
        );
    }
}
