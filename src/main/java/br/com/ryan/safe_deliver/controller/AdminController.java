package br.com.ryan.safe_deliver.controller;

import br.com.ryan.safe_deliver.dto.request.HospitalRegisterRequest;
import br.com.ryan.safe_deliver.dto.request.UserRegisterRequest;
import br.com.ryan.safe_deliver.dto.response.HospitalRegisterResponse;
import br.com.ryan.safe_deliver.dto.response.UserRegisterResponse;
import br.com.ryan.safe_deliver.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping()
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/hospitals")
    public ResponseEntity<HospitalRegisterResponse> hospitalRegister(@Valid @RequestBody HospitalRegisterRequest hospitalRegisterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addHospital(hospitalRegisterRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> userRegister(@Valid @RequestBody UserRegisterRequest userRegisterRequest) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.registerUser(userRegisterRequest));
    }
}
