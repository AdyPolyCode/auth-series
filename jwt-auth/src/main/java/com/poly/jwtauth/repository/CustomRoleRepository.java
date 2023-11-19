package com.poly.jwtauth.repository;

import com.poly.jwtauth.entity.CustomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRoleRepository extends JpaRepository<CustomRole, Long> {
}
