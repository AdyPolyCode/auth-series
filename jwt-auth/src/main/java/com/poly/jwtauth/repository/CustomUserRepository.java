package com.poly.jwtauth.repository;

import com.poly.jwtauth.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

    @Query(value = "SELECT u FROM CustomUser u WHERE u.username = :value")
    Optional<CustomUser> findByUsername(String value);
}
