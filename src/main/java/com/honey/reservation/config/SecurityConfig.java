package com.honey.reservation.config;

import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.service.LoginService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers("/api/**").permitAll()
                        .mvcMatchers("/", "/reservations/search-date", "/users/login", "/users/sign-up").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/users/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/loginProcess")
                        .defaultSuccessUrl("/")
                        .failureUrl("/users/login?error=1")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                        .logoutSuccessUrl("/")
                )
                .csrf(csrf -> csrf.ignoringAntMatchers("/api/**"))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(LoginService loginService) {
        return username -> loginService
                .loadUserByUsername(username)
                .map(UserAccountUserDetails::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - loginId : " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
