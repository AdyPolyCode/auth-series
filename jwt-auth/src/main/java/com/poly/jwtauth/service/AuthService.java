package com.poly.jwtauth.service;

import com.poly.jwtauth.entity.CustomRole;
import com.poly.jwtauth.entity.CustomUser;
import com.poly.jwtauth.repository.CustomRoleRepository;
import com.poly.jwtauth.repository.CustomUserRepository;
import com.poly.jwtauth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserRepository userRepository;

    @Autowired
    private CustomRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public String login(CustomUser user) {
        CustomUser foundUser = userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        if(!encoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        Map<String, ?> map = new HashMap<>(){
            {
                put("roles", foundUser.getAuthorities());
            }
        };

        return jwtService.create(foundUser.getUsername(), map);
    }

    public String register(CustomUser user) {
        Optional<CustomRole> role = roleRepository.findById(1L);

        user.setAuthority(role.get());

        CustomUser createdUser = userRepository.save(user);

        Map<String, ?> map = new HashMap<>(){
            {
                put("roles", createdUser.getAuthorities());
            }
        };

        return jwtService.create(createdUser.getUsername(), map);
    }
}
