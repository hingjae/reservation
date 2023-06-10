package com.honey.reservation.dto;

import com.honey.reservation.domain.Manager;

public record ManagerDto(
        String loginId, String name
) {
    public static ManagerDto of(String loginId, String name) {
        return new ManagerDto(loginId, name);
    }

    public static ManagerDto from(Manager entity) {
        return new ManagerDto(entity.getLoginId(), entity.getName());
    }
}
