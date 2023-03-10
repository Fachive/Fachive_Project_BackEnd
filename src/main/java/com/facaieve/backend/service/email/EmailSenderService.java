package com.facaieve.backend.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    @Autowired
    JavaMailSender javaMailSender;
    @Async
    public void sendEmail(SimpleMailMessage email) {
        log.info("sending email for authentication to user's mail");
        javaMailSender.send(email);
    }
}
