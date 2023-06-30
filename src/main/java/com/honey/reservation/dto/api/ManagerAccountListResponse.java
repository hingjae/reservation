package com.honey.reservation.dto.api;

import com.honey.reservation.dto.ManagerAccountDto;

import java.util.List;

public record ManagerAccountListResponse(
        List<ManagerAccountDto> managers
) {
    public static ManagerAccountListResponse from(List<ManagerAccountDto> managerAccountDtos) {
        return new ManagerAccountListResponse(managerAccountDtos);
    }
}
