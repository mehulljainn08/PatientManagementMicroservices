package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> optionalToken = authService.authenticate(loginRequestDTO);

        if (optionalToken.isPresent()) {
            return ResponseEntity.ok(new LoginResponseDTO(optionalToken.get()));
        } else {
            return ResponseEntity.status(401).body("wrong credentials");
        }
    }



}
