package com.studentportal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentportal.entity.User;
import com.studentportal.repository.UserRepository;
import com.studentportal.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<User> optionalUser = userRepository.findByUserId(email);
        User user = optionalUser.orElse(
                User.builder().userId(email).isVerified(true).build()
        );
        user.setVerified(true);
        userRepository.save(user);

        String token = jwtService.generateToken(email);

        // Set response headers and write token as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(),
                new com.studentportal.dto.AuthResponse(token));
    }
}
