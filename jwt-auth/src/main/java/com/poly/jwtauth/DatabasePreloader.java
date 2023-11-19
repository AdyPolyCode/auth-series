package com.poly.jwtauth;

import com.poly.jwtauth.entity.CustomRole;
import com.poly.jwtauth.entity.CustomUser;
import com.poly.jwtauth.repository.CustomRoleRepository;
import com.poly.jwtauth.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabasePreloader {

    @Autowired
    private CustomUserRepository userRepository;

    @Autowired
    private CustomRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            CustomRole role = new CustomRole();
            role.setRole("ROLE_USER");

            roleRepository.saveAndFlush(role);

            CustomUser user = new CustomUser();
            user.setUsername("username");
            user.setPassword(encoder.encode("password"));
            user.setAuthority(role);

            userRepository.saveAndFlush(user);
        };
    }

}
