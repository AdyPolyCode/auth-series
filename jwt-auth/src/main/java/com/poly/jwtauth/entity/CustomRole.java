package com.poly.jwtauth.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "custom_role")
public class CustomRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CustomRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
