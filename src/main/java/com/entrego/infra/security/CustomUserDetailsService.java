package com.entrego.infra.security;

import com.entrego.repositories.StoreRepository;
import com.entrego.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails;

        var user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(),
                    user.get().getPassword(),
                    Collections.singletonList(() -> "ROLE_USER")
            );
        } else {
            var store = storeRepository.findStoreByEmail(email);
            if (store.isPresent()) {
                userDetails = new org.springframework.security.core.userdetails.User(
                        store.get().getEmail(),
                        store.get().getPassword(),
                        Collections.singletonList(() -> "ROLE_STORE")
                );
            } else {
                throw new UsernameNotFoundException("User or Store not found");
            }
        }

        return userDetails;
    }
}
