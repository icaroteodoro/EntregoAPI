package com.entrego.controllers;

import com.entrego.domain.Store;
import com.entrego.domain.User;
import com.entrego.dtos.LoginUserRequestDTO;
import com.entrego.dtos.LoginUserResponseDTO;
import com.entrego.dtos.RegisterStoreRequestDTO;
import com.entrego.dtos.RegisterUserRequestDTO;
import com.entrego.infra.security.TokenService;
import com.entrego.repositories.StoreRepository;
import com.entrego.repositories.UserRepository;
import com.entrego.services.StoreService;
import com.entrego.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginUserRequestDTO body) {
        User user = this.userRepository.findUserByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateTokenUser(user);
            return ResponseEntity.ok(new LoginUserResponseDTO(user.getFirstName(), token));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterUserRequestDTO body) {
        try{
            User newUser = this.userService.createUser(body);
            String token = this.tokenService.generateTokenUser(newUser);
            return ResponseEntity.ok(new LoginUserResponseDTO( newUser.getFirstName(), token));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/store/login")
    public ResponseEntity<?> storeLogin(@RequestBody LoginUserRequestDTO body) throws Exception {
        Store store = this.storeService.findStoreByEmail(body.email());
        if(passwordEncoder.matches(body.password(), store.getPassword())) {
            String token = this.tokenService.generateTokenStore(store);
            return ResponseEntity.ok(new LoginUserResponseDTO(store.getName(), token));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/store/register")
    public ResponseEntity<?> storeRegister(@RequestBody RegisterStoreRequestDTO body) {
        try{
            Store newStore = this.storeService.createStore(body);
            String token = this.tokenService.generateTokenStore(newStore);
            return ResponseEntity.ok(new LoginUserResponseDTO( newStore.getName(), token));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
