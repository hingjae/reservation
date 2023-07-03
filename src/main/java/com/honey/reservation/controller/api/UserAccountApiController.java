package com.honey.reservation.controller.api;

import com.honey.reservation.dto.api.UserAccountDto;
import com.honey.reservation.dto.api.UserReservationResponse;
import com.honey.reservation.repository.UserAccountRepository;
import com.honey.reservation.repository.api.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@RequestMapping("/api/userAccounts")
@RestController
public class UserAccountApiController {

    private final UserAccountRepository userAccountRepository;
    private final ReservationQueryRepository reservationQueryRepository;

    @GetMapping
    public Page<UserAccountDto> userAccounts(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String searchValue
    ) {
//        return userAccountRepository.findAll(pageable).map(UserAccountDto::from);
        return userAccountRepository.searchBySearchValue(pageable, searchValue);
    }


    @GetMapping("/{userId}")
    public UserReservationResponse userReservations(@PathVariable("userId") String userId) {
        return UserReservationResponse.from(
                userAccountRepository.findNameByLoginId(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디")),
                reservationQueryRepository.findByUserId(userId)
        );
    }

    @DeleteMapping("/{userId}")
    public void deleteUserAccount(@PathVariable("userId") String userId) {
        userAccountRepository.deleteById(userId);
    }
}
