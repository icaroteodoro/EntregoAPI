package com.entrego.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.entrego.domain.Store;
import com.entrego.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateTokenUser(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return  JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate(1))
                    .sign(algorithm);

        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String generateRefreshTokenUser(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return  JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate(720))
                    .sign(algorithm);

        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String generateTokenStore(Store store) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return  JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(store.getEmail())
                    .withExpiresAt(this.generateExpirationDate(2))
                    .sign(algorithm);

        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String generateRefreshTokenStore(Store store) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return  JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(store.getEmail())
                    .withExpiresAt(this.generateExpirationDate(720))
                    .sign(algorithm);

        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }


    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException exception) {
            return null;
        }
    }


    private Instant generateExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isTokenExpired(String token) {
        try {
            String payload = extractPayload(token);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);

            if (!payloadMap.containsKey("exp")) {
                return true; // Se não houver campo "exp", consideramos o token inválido
            }

            long expTimestamp = ((Number) payloadMap.get("exp")).longValue() * 1000; // Convertendo para milissegundos
            return expTimestamp < System.currentTimeMillis(); // Retorna true se o token estiver expirado

        } catch (Exception e) {
            return true; // Em caso de erro na leitura do token, assumimos que ele está inválido ou expirado
        }
    }

    private String extractPayload(String token) throws IOException {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IOException("Invalid JWT token format");
        }
        return new String(Base64.getUrlDecoder().decode(parts[1]));
    }


}
