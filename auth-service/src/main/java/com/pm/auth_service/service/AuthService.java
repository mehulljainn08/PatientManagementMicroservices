package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginRequestDTO;
import com.pm.auth_service.model.User;
import com.pm.auth_service.repository.UserRepository;
import com.pm.auth_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<User> user = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()));

        return user.map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole())).orElse(null);



    }
}
