package com.honey.reservation.dto.request;

import com.honey.reservation.dto.UserAccountDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력하세요") @Length(max = 50, message = "아이디는 50자를 넘을 수 없습니다.") private String loginId;
    @NotBlank(message = "비민번호를 입력하세요") @Length(max = 50, message = "비밀번호는 50자를 넘을 수 없습니다.") private String password;
    @NotBlank(message = "이름을 입력하세요") @Length(max = 10, message = "이름은 10자를 넘을 수 없습니다.") private String name;
    private String phoneNumber;

    private SignUpRequest() {}

    public static SignUpRequest of() {
        return new SignUpRequest();
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(loginId, password, name, phoneNumber);
    }
}
