package com.poly.security;

import com.poly.model.AuthUser;
import com.poly.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsManager {
    private AuthUserRepository repository;

    @Autowired
    public JpaUserDetailsService(AuthUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> userDetails = repository.findByUsername(username);

        if(userDetails.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " was not found!");
        }

        return userDetails.get();
    }

    @Override
    public void createUser(UserDetails user) {
        repository.saveAndFlush((AuthUser) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        repository.saveAndFlush((AuthUser) user);
    }

    @Override
    public void deleteUser(String username) {
        AuthUser user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " was not found!"));
        repository.delete(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        return;
    }

    @Override
    public boolean userExists(String username) {
        return repository.findByUsername(username).isPresent();
    }
}
