package com.honey.reservation.controller.api;

import com.honey.reservation.dto.ManagerAccountDto;
import com.honey.reservation.dto.api.ManagerAccountListResponse;
import com.honey.reservation.repository.ManagerAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/managerAccounts")
@RestController
public class ManagerAccountApiController {

    private final ManagerAccountRepository managerAccountRepository;

    @GetMapping
    public ManagerAccountListResponse managerAccounts() {
        List<ManagerAccountDto> managerAccountDtos = managerAccountRepository.findAll().stream()
                .map(ManagerAccountDto::from)
                .toList();
        return ManagerAccountListResponse.from(managerAccountDtos);
    }
}
