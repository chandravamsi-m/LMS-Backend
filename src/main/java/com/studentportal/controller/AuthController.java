package com.studentportal.controller;

import com.studentportal.dto.AuthRequest;
import com.studentportal.dto.AuthResponse;
import com.studentportal.dto.OtpRequest;
import com.studentportal.entity.User;
import com.studentportal.repository.UserRepository;
import com.studentportal.service.AuthService;
import com.studentportal.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // Request OTP via mobile number
    @PostMapping("/request-otp")
    public String requestOtp(@RequestBody OtpRequest request) {
        return authService.requestOtp(request);
    }

    // Verify OTP and return JWT
    @PostMapping("/verify-otp")
    public AuthResponse verifyOtp(@RequestBody AuthRequest request) {
        String token = authService.verifyOtp(request);
        return new AuthResponse(token);
    }

    // Redirect to Google OAuth Login
    @GetMapping("/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/oauth-success")
    public AuthResponse oauthSuccess(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            throw new RuntimeException("OAuth2 principal is null");
        }

        String email = principal.getAttribute("email");

        Optional<User> optionalUser = userRepository.findByUserId(email);
        User user = optionalUser.orElseGet(() ->
                User.builder().userId(email).isVerified(true).build()
        );

        user.setVerified(true);
        userRepository.save(user);

        String token = jwtService.generateToken(email);
        return new AuthResponse(token);
    }

}
