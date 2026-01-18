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

    @Autowired
    private com.entrego.repositories.AccountRepository accountRepository;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/ws");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if(token != null){
            if(tokenService.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }
        if (login != null) {
            Object authenticatedEntity = null;
            List<SimpleGrantedAuthority> authorities;

            var account = accountRepository.findAccountByEmail(login);
            
            if(account.isPresent()) {
                if(account.get().getRole().equals("USER")) {
                    var user = userRepository.findUserByAccount(account.get());
                    if(user.isPresent()) {
                        authenticatedEntity = user.get();
                        authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                    } else {
                        throw new RuntimeException("User linked to Account not found");
                    }
                } else if(account.get().getRole().equals("STORE")) {
                    var store = storeRepository.findStoreByAccount(account.get());
                    if(store.isPresent()) {
                        authenticatedEntity = store.get();
                        authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_STORE"));
                    } else {
                        throw new RuntimeException("Store linked to Account not found");
                    }
                } else {
                    throw new RuntimeException("Unknown Role");
                }
            } else {
                throw new RuntimeException("Account not found");
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
