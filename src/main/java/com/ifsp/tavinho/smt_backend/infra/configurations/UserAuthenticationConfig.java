package com.ifsp.tavinho.smt_backend.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ifsp.tavinho.smt_backend.infra.services.UserDetailsLoader;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserAuthenticationConfig {

    private final UserDetailsLoader userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(this.userDetailsService);

        provider.setPasswordEncoder(this.passwordEncoder);

        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
