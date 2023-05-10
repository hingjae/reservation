package com.honey.reservation.dto;

import com.honey.reservation.domain.Manager;

public record ManagerDto(
        Long id, String name
) {
    public static ManagerDto of(Long id, String name) {
        return new ManagerDto(id, name);
    }

    public static ManagerDto from(Manager entity) {
        return new ManagerDto(entity.getId(), entity.getName());
    }
}
