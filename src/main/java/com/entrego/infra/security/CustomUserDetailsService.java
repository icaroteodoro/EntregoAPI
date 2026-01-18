package com.entrego.infra.security;

import com.entrego.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails;

        var account = accountRepository.findAccountByEmail(email);
        if (account.isPresent()) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    account.get().getEmail(),
                    account.get().getPassword(),
                    Collections.singletonList(() -> "ROLE_" + account.get().getRole())
            );
        } else {
             throw new UsernameNotFoundException("User or Store not found");
        }

        return userDetails;
    }
}
