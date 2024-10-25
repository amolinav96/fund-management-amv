package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.config.MailConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.Properties;

import static com.co.fundmanagement.enums.MessageEnum.MESSAGE_SEND_EMAIL;
import static com.co.fundmanagement.enums.MessageEnum.SUBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Spy
    private JavaMailSenderImpl mailSender;

    @InjectMocks
    private EmailService emailService;

    private static final String FROM_EMAIL = "test@gmail.com";
    private static final String PASSWORD = "test-password";
    private static final String TO_EMAIL = "recipient@test.com";

    @BeforeEach
    void setUp() {
        MailConfig mailConfig = new MailConfig();
        ReflectionTestUtils.setField(mailConfig, "emailUser", FROM_EMAIL);
        ReflectionTestUtils.setField(mailConfig, "password", PASSWORD);

        mailSender = spy((JavaMailSenderImpl) mailConfig.getJavaMailSender());

        ReflectionTestUtils.setField(emailService, "emailUser", FROM_EMAIL);
        ReflectionTestUtils.setField(emailService, "mailSender", mailSender);

        Properties props = mailSender.getJavaMailProperties();
        assertEquals("smtp", props.getProperty("mail.transport.protocol"));
        assertEquals("true", props.getProperty("mail.smtp.auth"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.enable"));
        assertEquals("true", props.getProperty("mail.debug"));
    }

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        Mono<Void> result = emailService.sendEmail(TO_EMAIL);

        StepVerifier.create(result)
                .verifyComplete();

        verify(mailSender, times(1)).send((SimpleMailMessage) argThat(message -> {
            SimpleMailMessage mailMessage = (SimpleMailMessage) message;
            return Objects.equals(mailMessage.getFrom(), FROM_EMAIL) &&
                    Objects.requireNonNull(mailMessage.getTo())[0].equals(TO_EMAIL) &&
                    Objects.equals(mailMessage.getSubject(), SUBJECT.getMessage()) &&
                    Objects.equals(mailMessage.getText(), MESSAGE_SEND_EMAIL.getMessage());
        }));
    }

    @Test
    void mailSenderConfiguration_ShouldBeCorrect() {
        assertEquals("smtp.gmail.com", mailSender.getHost());
        assertEquals(587, mailSender.getPort());
        assertEquals(FROM_EMAIL, mailSender.getUsername());
        assertEquals(PASSWORD, mailSender.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        assertEquals("smtp", props.getProperty("mail.transport.protocol"));
        assertEquals("true", props.getProperty("mail.smtp.auth"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.enable"));
        assertEquals("true", props.getProperty("mail.debug"));
    }
}