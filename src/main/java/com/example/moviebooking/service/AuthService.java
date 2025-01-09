package com.example.moviebooking.service;

import com.example.moviebooking.domain.User;
import com.example.moviebooking.dto.LoginRequest;
import com.example.moviebooking.dto.SignupRequest;
import com.example.moviebooking.repository.UserRepository;
import com.example.moviebooking.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String signup(SignupRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
        userRepository.save(user);
        return "signup success";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // JWT 발급
        return jwtTokenProvider.createToken(user.getEmail(), user.getId());
    }
}
