package com.poly.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private JpaUserDetailsService service;

    @Autowired
    public SecurityConfig(JpaUserDetailsService service) {
        this.service = service;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.csrf(cfg -> cfg.disable());
        http.cors(cfg -> cfg.disable());
        http.headers(cfg1 -> cfg1.frameOptions(cfg2 -> cfg2.disable()));

        http.authorizeHttpRequests(cfg -> {
            cfg
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/console")).permitAll()
                    .requestMatchers(mvc.pattern("/demo/role")).hasRole("ADMIN")
                    .anyRequest().authenticated();
        });

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(JpaUserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) {
        return builder.getObject();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
