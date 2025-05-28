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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain authenticationChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                //allow only authenticated users
                .authorizeHttpRequests(c ->
                        c
                                .requestMatchers("/students").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers("/students/*").hasAnyRole("ADMIN", "STUDENT")
                                .requestMatchers("/courses/*").hasRole("ADMIN")
                )
                .csrf(
                        configurer -> configurer
                                .csrfTokenRepository(csrfTokenRepository())
                )
                .addFilterAfter(new CsrfTokenFilter(), CsrfFilter.class)
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

    //todo: use another encoder than NoOpPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
