package com.poly.security;

import com.poly.model.AuthRole;
import com.poly.model.AuthUser;
import com.poly.repository.AuthRoleRepository;
import com.poly.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    private AuthUserRepository authUserRepository;

    private AuthRoleRepository authRoleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AppConfig(AuthUserRepository authUserRepository, AuthRoleRepository authRoleRepository, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.authRoleRepository = authRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            String pw = passwordEncoder.encode("password");

            AuthUser user = new AuthUser();
            user.setUsername("user1");
            user.setPassword(pw);
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            AuthRole role = new AuthRole();
            role.setAuthority("USER");
            role.setAuthUser(user);

            user.setAuthorities(Set.of(role));

            authUserRepository.save(user);
            authRoleRepository.save(role);
        };
    }
}
