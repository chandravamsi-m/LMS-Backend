package com.lms.modules.auth.service;

import com.lms.modules.auth.dto.AuthRequest;
import com.lms.modules.auth.dto.AuthResponse;
import com.lms.modules.auth.util.OtpStore;
import com.lms.modules.notifications.service.EmailService;
import com.lms.modules.users.entity.Role;
import com.lms.modules.users.entity.User;
import com.lms.modules.users.repository.UserRepository;
import com.lms.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final EmailService emailService;

    public String requestOtp(AuthRequest request) {
        String contact = request.getEmail() != null ? request.getEmail() : request.getMobile();
        if (contact == null) {
            throw new RuntimeException("Either email or mobile is required");
        }

        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new RuntimeException("User with this email already exists");
        });

        validatePassword(request.getPassword());

        String otp = String.format("%06d", new Random().nextInt(1000000));
        OtpStore.storeOtp(contact, otp);

        if (request.getEmail() != null) {
            emailService.sendEmailOtp(request.getEmail(), otp);
            return "OTP sent successfully to email";
        }
        
        log.info("OTP for {}: {}", contact, otp); // Placeholder for SMS
        return "OTP generated successfully";
    }

    public AuthResponse verifyOtpAndRegister(AuthRequest request) {
        String contact = request.getEmail() != null ? request.getEmail() : request.getMobile();
        String storedOtp = OtpStore.getOtp(contact);

        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = User.builder()
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .isVerified(true)
                .role(Role.USER)
                .build();

        userRepository.save(user);
        OtpStore.removeOtp(contact);

        String accessToken = tokenProvider.generateAccessToken(contact, user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(contact);
        
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(String identifier, String password) {
        User user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByMobile(identifier))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isVerified() || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials or unverified account");
        }

        String accessToken = tokenProvider.generateAccessToken(identifier, user.getRole());
        String refreshToken = tokenProvider.generateRefreshToken(identifier);
        
        return new AuthResponse(accessToken, refreshToken);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*\\d.*") ||
                !password.matches(".*[!@#$%^&*()].*")) {

            throw new RuntimeException("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
        }
    }
}
