package tda.spring.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    public ResponseEntity<String> mailService() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("son_1122@hotmail.com");
        message.setSubject("Test Email");
        message.setText("This is a test email");

        mailSender.send(message);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
