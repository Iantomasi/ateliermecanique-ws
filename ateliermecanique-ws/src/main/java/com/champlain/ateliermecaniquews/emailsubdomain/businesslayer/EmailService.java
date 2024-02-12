package com.champlain.ateliermecaniquews.emailsubdomain.businesslayer;
import lombok.Generated;
import org.thymeleaf.engine.TemplateData;

import javax.mail.MessagingException;
import java.util.Map;

@Generated
public interface EmailService {
    int sendEmail(String recipient, String subject, String text) throws MessagingException;
    int sendEmail(String recipient, String subject, String template, Map<String, String> parameters) throws MessagingException;
}
