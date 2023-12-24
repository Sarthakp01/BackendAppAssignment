package com.example.SpringApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] WHITE_LIST_URL = {"/user/register", "/user/authenticate", "/token/refreshToken", "/vendor/register"
            };
    private static final String[] PROTECTED_LIST_URL = {"/admin/approve/**","/admin/toggleOfflinePayments/**", "/user/addFunds",
            "/user/addOfflineFunds", "/admin/vendor/approve/**", "/transaction/online", "/transaction/online/verify",
            "/transaction/offline", "/admin/checkFlaggedTransaction/**", "/vendor/transferFundsToPersonalWallet"
            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req->req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers(PROTECTED_LIST_URL)
                        .authenticated())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
