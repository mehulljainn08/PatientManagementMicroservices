package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Validate token")
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
        String token = authHeader.substring(7);

        return authService.validateToken(token) ?
                ResponseEntity.ok("Token is valid") :
                ResponseEntity.status(401).body("Token is invalid");

    }



}
