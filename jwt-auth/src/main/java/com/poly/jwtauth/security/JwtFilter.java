package com.poly.jwtauth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtFilter extends OncePerRequestFilter {

    private CustomUserDetailsService userDetailsService;

    private JwtService jwtService;

    public JwtFilter(CustomUserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = getAuthToken(request);

        if (isValidAuthToken(header)) {
            String token = header.split(" ")[1];
            SecurityContext context = SecurityContextHolder.getContext();
            String username = jwtService.getUserName(token);

            if (context.getAuthentication() == null) {
                UserDetails details = userDetailsService.loadUserByUsername(username);

                if (jwtService.isExpired(token)) {
                    setAuthContext(context, details, request);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getAuthToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private boolean isValidAuthToken(String token) {
        return Objects.nonNull(token) && token.startsWith("Bearer ");
    }

    private void setAuthContext(SecurityContext context, UserDetails details, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authentication);
    }
}
