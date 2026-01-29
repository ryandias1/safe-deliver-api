package br.com.ryan.safe_deliver.security;

import br.com.ryan.safe_deliver.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("safe-deliver")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("safe-deliver")
                .build()
                .verify(token)
                .getSubject();
    }
}
