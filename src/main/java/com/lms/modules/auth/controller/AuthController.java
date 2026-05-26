package com.lms.modules.auth.controller;

import com.lms.modules.auth.dto.AuthRequest;
import com.lms.modules.auth.dto.AuthResponse;
import com.lms.modules.auth.service.AuthService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/request-otp")
    @Operation(summary = "Request OTP for registration")
    public ResponseEntity<ApiResponse<String>> requestOtp(@RequestBody AuthRequest request) {
        String result = authService.requestOtp(request);
        return ResponseEntity.ok(ApiResponse.success(result, "OTP request processed"));
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP and complete registration")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@RequestBody AuthRequest request) {
        AuthResponse response = authService.verifyOtpAndRegister(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Registration successful"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email/mobile and password")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        String identifier = request.getEmail() != null ? request.getEmail() : request.getMobile();
        AuthResponse response = authService.login(identifier, request.getPassword());
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }
}
