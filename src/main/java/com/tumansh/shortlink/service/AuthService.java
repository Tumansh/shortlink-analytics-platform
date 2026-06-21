package com.tumansh.shortlink.service;

import com.tumansh.shortlink.dto.request.LoginRequest;
import com.tumansh.shortlink.dto.request.RegisterRequest;
import com.tumansh.shortlink.dto.response.AuthResponse;
import com.tumansh.shortlink.entity.Role;
import com.tumansh.shortlink.entity.User;
import com.tumansh.shortlink.exception.EmailAlreadyExistsException;
import com.tumansh.shortlink.exception.InvalidCredentialsException;
import com.tumansh.shortlink.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {

        if(userRepo.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException(
                    "Email already exists"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());

        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        user.setRole(Role.USER);

        user.setCreatedAt(
                LocalDateTime.now()
        );

        userRepo.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        return new AuthResponse(
                "LOGIN_SUCCESS"
        );
    }
}