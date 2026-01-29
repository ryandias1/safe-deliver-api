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
    private Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("safe-deliver")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(this.algorithm);
    }

    public String validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("safe-deliver")
                .build()
                .verify(token)
                .getSubject();
    }
}
