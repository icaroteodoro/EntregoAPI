package com.entrego.controllers;

import com.entrego.domain.Store;
import com.entrego.domain.User;
import com.entrego.dtos.*;
import com.entrego.infra.security.TokenService;
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
            String refreshToken = this.tokenService.generateRefreshTokenUser(user);
            return ResponseEntity.ok(new LoginUserResponseDTO(user.getFirstName() +" "+ user.getLastName(), token, refreshToken));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterUserRequestDTO body) {
        try{
            User newUser = this.userService.createUser(body);
            String token = this.tokenService.generateTokenUser(newUser);
            String refreshToken  = this.tokenService.generateRefreshTokenUser(newUser);
            return ResponseEntity.ok(new LoginUserResponseDTO( newUser.getFirstName() +" "+ newUser.getLastName(), token, refreshToken));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/store/login")
    public ResponseEntity<?> storeLogin(@RequestBody LoginUserRequestDTO body) throws Exception {
        Store store = this.storeService.findStoreByEmail(body.email());
        System.out.println(store.getName());
        if(passwordEncoder.matches(body.password(), store.getPassword())) {
            String token = this.tokenService.generateTokenStore(store);
            String refreshToken = this.tokenService.generateRefreshTokenStore(store);
            return ResponseEntity.ok(new LoginUserResponseDTO(store.getName(), token, refreshToken));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/store/register")
    public ResponseEntity<?> storeRegister(@RequestBody RegisterStoreRequestDTO body) {
        try{
            Store newStore = this.storeService.createStore(body);
            String token = this.tokenService.generateTokenStore(newStore);
            String refreshToken = this.tokenService.generateRefreshTokenStore(newStore);
            return ResponseEntity.ok(new LoginUserResponseDTO(newStore.getName(), token, refreshToken));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/user/refresh-token")
    public ResponseEntity<?> createNewTokenForUser(@RequestBody RequestRefreshToken data) throws Exception {
        try{
            String refresh = this.tokenService.validateToken(data.refreshToken());
            User user = this.userService.findUserByEmail(refresh);
            String token = this.tokenService.generateTokenUser(user);
            String refreshToken = this.tokenService.generateRefreshTokenUser(user);
            return ResponseEntity.ok(new LoginUserResponseDTO(user.getFirstName() +" "+ user.getLastName(), token, refreshToken));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/store/refresh-token")
    public ResponseEntity<?> createNewTokenForStore(@RequestBody RequestRefreshToken data) throws Exception {
        try{
            String refresh = this.tokenService.validateToken(data.refreshToken());
            Store store = this.storeService.findStoreByEmail(refresh);
            String token = this.tokenService.generateTokenStore(store);
            String refreshToken = this.tokenService.generateRefreshTokenStore(store);
            return ResponseEntity.ok(new LoginUserResponseDTO(store.getName(), token, refreshToken));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
