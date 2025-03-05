package com.entrego.infra.security;

import com.entrego.repositories.StoreRepository;
import com.entrego.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            Object authenticatedEntity = null;
            List<SimpleGrantedAuthority> authorities;

            var user = userRepository.findUserByEmail(login);
            if (user.isPresent()) {
                authenticatedEntity = user.get();
                authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                var store = storeRepository.findStoreByEmail(login);
                if (store.isPresent()) {
                    authenticatedEntity = store.get();
                    authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_STORE"));
                } else {
                    throw new RuntimeException("User or Store not found");
                }
            }

            var authentication = new UsernamePasswordAuthenticationToken(authenticatedEntity, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
