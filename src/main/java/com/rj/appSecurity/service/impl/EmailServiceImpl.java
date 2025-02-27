package com.rj.appSecurity.service.impl;

import com.rj.appSecurity.exception.ApiException;
import com.rj.appSecurity.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.rj.appSecurity.utils.EmailUtils.getEmailMessage;
import static com.rj.appSecurity.utils.EmailUtils.getResetPasswordMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private static final String REST_PASSWORD_REQUEST = "Rest Password Request";

    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token) {
        try {
            log.info("Attempting to send new account verification email to: {}", email);

            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getEmailMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}. Error: {}", email, e.getMessage(), e);
            throw new ApiException("unable to send Email");
        }
    }

    @Override
    @Async
    public void sendPasswordRestEmail(String name, String email, String token) {
        try {
            log.info("Attempting to send sendPasswordRestEmail email to: {}", email);

            var message = new SimpleMailMessage();
            message.setSubject(REST_PASSWORD_REQUEST);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getResetPasswordMessage(name, host, token));
            sender.send(message);
        } catch (Exception e) {
            log.error("Failed to send sendPasswordRestEmail email to: {}. Error: {}", email, e.getMessage(), e);
            throw new ApiException("unable to send Email");
        }
    }
}
