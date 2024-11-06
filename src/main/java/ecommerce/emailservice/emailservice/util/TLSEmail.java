package ecommerce.emailservice.emailservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.emailservice.emailservice.dto.EmailUserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class TLSEmail {


    private EmailUtil emailUtil;
    private ObjectMapper objectMapper;

    public TLSEmail(EmailUtil emailUtil, ObjectMapper objectMapper) {
        this.emailUtil = emailUtil;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "sendEmail", groupId = "EmailService")
    public void sendEmail(String message) {

        EmailUserDto emailUserDto = null;

        try {
            emailUserDto =objectMapper.readValue(message, EmailUserDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("erilshaji05@gmail.com", "zdpbtqxjxpklzlnt");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, emailUserDto.getTo(),emailUserDto.getSubject(), emailUserDto.getBody());

    }


}