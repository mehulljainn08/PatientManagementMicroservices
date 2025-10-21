package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.repository.UserRepository;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<User> userOptional = userService.findByEmail(loginRequestDTO);

        // If the user doesn't exist, return early
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // --- ADD THESE LOGS ---
        log.info("Plaintext password from request: '{}'", loginRequestDTO.getPassword());
        log.info("Hashed password from database:    '{}'", user.getPassword());

        boolean passwordsMatch = passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());
        log.info("Do the passwords match? {}", passwordsMatch);
        // --- END OF LOGS ---

        if (passwordsMatch) {
            return Optional.of(jwtUtil.generateToken(user.getEmail(), user.getRole()));
        } else {
            return Optional.empty();
        }
    }

    public boolean validateToken(String token) {

        try {
            jwtUtil.validateToken(token);
            return true;
        } catch ( JwtException jwtException) {
            return false;
        }
    }
}