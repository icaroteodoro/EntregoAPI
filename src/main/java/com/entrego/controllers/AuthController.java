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

    @Autowired
    private com.entrego.repositories.AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequestDTO body) throws Exception {
        com.entrego.domain.Account account = this.accountRepository.findAccountByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(body.password(), account.getPassword())) {
            String token = this.tokenService.generateToken(account);
            String refreshToken = this.tokenService.generateRefreshToken(account);
            String name = "";

            if(account.getRole().equals("USER")) {
                User user = this.userService.findUserByEmail(body.email());
                name = user.getFirstName() + " " + user.getLastName();
            } else {
                 Store store = this.storeService.findStoreByEmail(body.email());
                 name = store.getName();
            }

            return ResponseEntity.ok(new LoginUserResponseDTO(name, token, refreshToken));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterUserRequestDTO body) {
        try{
            User newUser = this.userService.createUser(body);
            String token = this.tokenService.generateToken(newUser.getAccount());
            String refreshToken  = this.tokenService.generateRefreshToken(newUser.getAccount());
            return ResponseEntity.ok(new LoginUserResponseDTO( newUser.getFirstName() +" "+ newUser.getLastName(), token, refreshToken));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/store/register")
    public ResponseEntity<?> storeRegister(@RequestBody RegisterStoreRequestDTO body) {
        try{
            Store newStore = this.storeService.createStore(body);
            String token = this.tokenService.generateToken(newStore.getAccount());
            String refreshToken = this.tokenService.generateRefreshToken(newStore.getAccount());
            return ResponseEntity.ok(new LoginUserResponseDTO(newStore.getName(), token, refreshToken));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RequestRefreshToken data) throws Exception {
        try{
            String email = this.tokenService.validateToken(data.refreshToken());
            com.entrego.domain.Account account = this.accountRepository.findAccountByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = this.tokenService.generateToken(account);
            String refreshToken = this.tokenService.generateRefreshToken(account);
            String name = "";

             if(account.getRole().equals("USER")) {
                User user = this.userService.findUserByEmail(email);
                name = user.getFirstName() + " " + user.getLastName();
            } else {
                 Store store = this.storeService.findStoreByEmail(email);
                 name = store.getName();
            }

            return ResponseEntity.ok(new LoginUserResponseDTO(name, token, refreshToken));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
