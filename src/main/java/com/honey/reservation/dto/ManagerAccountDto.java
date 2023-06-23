package com.honey.reservation.dto;

import com.honey.reservation.domain.ManagerAccount;

public record ManagerAccountDto(
        Long id, String name
) {

    public static ManagerAccountDto of(Long id, String name) {
        return new ManagerAccountDto(id, name);
    }

    public static ManagerAccountDto from(ManagerAccount entity) {
        return ManagerAccountDto.of(entity.getId(), entity.getName());
    }
}
