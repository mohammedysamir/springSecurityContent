package com.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain authenticationChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                //allow only authenticated users
                .authorizeHttpRequests(c -> c.anyRequest().authenticated())
                .build();
    }


    @Bean
    public UserDetailsService users() {
        List<UserDetails> userList = List.of(
                User.withUsername("Mohammed")
                        .password("123456")
                        .authorities("ROLE_ADMIN", "FETCH", "CREATE", "DELETE")
                        .build()
        );

        return new InMemoryUserDetailsManager(userList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
