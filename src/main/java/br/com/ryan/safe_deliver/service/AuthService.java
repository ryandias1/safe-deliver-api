package br.com.ryan.safe_deliver.service;

import br.com.ryan.safe_deliver.dto.request.UserLoginRequest;
import br.com.ryan.safe_deliver.dto.request.UserRegisterRequest;
import br.com.ryan.safe_deliver.dto.response.UserLoginResponse;
import br.com.ryan.safe_deliver.entity.User;
import br.com.ryan.safe_deliver.repository.UserRepository;
import br.com.ryan.safe_deliver.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserLoginResponse login(UserLoginRequest userLoginRequest) throws Exception {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(userLoginRequest.email(), userLoginRequest.password());
        Authentication auth = authenticationManager.authenticate(userAndPass);
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);
        return new UserLoginResponse(token);
    }
}
