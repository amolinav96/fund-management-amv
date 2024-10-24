package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.co.fundmanagement.enums.MessageEnum.MESSAGE_SEND_EMAIL;
import static com.co.fundmanagement.enums.MessageEnum.SUBJECT;

@Service
public class EmailService implements IEmailService {

    @Value("${email.sender}")
    private String emailUser;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Mono<Void> sendEmail(String toUser) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailUser);
        mailMessage.setTo(toUser);
        mailMessage.setSubject(SUBJECT.getMessage());
        mailMessage.setText(MESSAGE_SEND_EMAIL.getMessage());

        mailSender.send(mailMessage);
        return Mono.empty();
    }
}
