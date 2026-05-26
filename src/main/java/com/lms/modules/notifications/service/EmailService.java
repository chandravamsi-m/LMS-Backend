package com.lms.modules.notifications.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmailOtp(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Your OTP for LMS Portal");

            String htmlContent = String.format("""
                <div style='font-family: Arial, sans-serif; padding: 20px; background: #f9f9f9;'>
                    <div style='max-width: 600px; margin: auto; background: white; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05);'>
                        <div style='padding: 30px; text-align: center;'>
                            <h2 style='color: #4A90E2;'>LMS Portal</h2>
                            <p style='font-size: 16px;'>Hi there,</p>
                            <p style='font-size: 16px;'>Use the following OTP to verify your account:</p>
                            <div style='margin: 30px 0;'>
                                <span style='display: inline-block; font-size: 28px; font-weight: bold; letter-spacing: 6px; color: #333; padding: 10px 20px; border: 2px dashed #4A90E2; border-radius: 6px; background: #f1f8ff;'>%s</span>
                            </div>
                            <p style='font-size: 14px; color: #777;'>This OTP is valid for 10 minutes. Do not share it with anyone.</p>
                        </div>
                    </div>
                </div>
                """, otp);

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("OTP email sent successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send OTP email to {}", to, e);
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
