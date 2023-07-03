package com.honey.reservation.repository.api;

import com.honey.reservation.dto.api.UserAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAccountRepositoryCustom {

    Page<UserAccountDto> searchBySearchValue(Pageable pageable, String searchValue);
}
