package br.com.ryan.safe_deliver.controller;

import br.com.ryan.safe_deliver.dto.request.UserLoginRequest;
import br.com.ryan.safe_deliver.dto.response.UserLoginResponse;
import br.com.ryan.safe_deliver.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) throws Exception {
        return ResponseEntity.ok(authService.login(userLoginRequest));
    }
}
