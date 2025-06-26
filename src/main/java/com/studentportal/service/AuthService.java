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
        OtpStore.storeOtp(request.getMobile(), otp);
        System.out.println("OTP for " + request.getMobile() + ": " + otp); // mock
        return "OTP sent successfully";
    }

    public String verifyOtp(AuthRequest request) {
        String storedOtp = OtpStore.getOtp(request.getMobile());
        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        // Register or update user
        Optional<User> optionalUser = userRepository.findByMobile(request.getMobile());
        User user = optionalUser.orElse(User.builder()
                .mobile(request.getMobile())
                .isVerified(true)
                .build());
        user.setVerified(true);
        userRepository.save(user);

        OtpStore.removeOtp(request.getMobile());
        return jwtService.generateToken(request.getMobile());
    }
}
