package com.studentportal.service;

import com.studentportal.dto.AuthRequest;
import com.studentportal.dto.OtpRequest;
import com.studentportal.entity.User;
import com.studentportal.repository.UserRepository;
import com.studentportal.util.OtpStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public String requestOtp(OtpRequest request) {
        String otp = String.valueOf(new Random().nextInt(899999) + 100000);
        OtpStore.storeOtp(request.getUserId(), otp);
        System.out.println("OTP for " + request.getUserId() + ": " + otp); // mock
        return "OTP sent successfully";
    }

    public String verifyOtp(AuthRequest request) {
        String storedOtp = OtpStore.getOtp(request.getUserId());
        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        // Register or update user
        Optional<User> optionalUser = userRepository.findByUserId(request.getUserId());
        User user = optionalUser.orElse(User.builder()
                .userId(request.getUserId())
                .isVerified(true)
                .build());
        user.setVerified(true);
        userRepository.save(user);

        OtpStore.removeOtp(request.getUserId());
        return jwtService.generateToken(request.getUserId());
    }
}
