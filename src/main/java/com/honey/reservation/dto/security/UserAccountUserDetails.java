package com.honey.reservation.dto.security;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record UserAccountUserDetails(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String name,
        String phoneNumber
) implements UserDetails {

    public static UserAccountUserDetails of(String username, String password, String name, String phoneNumber) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        return new UserAccountUserDetails(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                name,
                phoneNumber);
    }

    public static UserAccountUserDetails from(UserAccountDto dto) {
        return UserAccountUserDetails.of(dto.loginId(), dto.password(), dto.name(), dto.phoneNumber());
    }

    public UserAccount toEntity(PasswordEncoder passwordEncoder) {
        return UserAccount.of(username, passwordEncoder.encode(password), name, phoneNumber);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(username, password, name, phoneNumber);
    }

    @Override public String getUsername() {return username;}
    @Override public String getPassword() {return password;}
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() {return true;}
    @Override public boolean isEnabled() {return true;}

    public enum RoleType {
        USER("ROLE_USER");

        @Getter private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
