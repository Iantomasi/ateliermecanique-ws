package com.champlain.ateliermecaniquews.emailsubdomain.presentationlayer;
import com.champlain.ateliermecaniquews.emailsubdomain.businesslayer.EmailService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin(origins = {"http://localhost:3000"},allowCredentials = "true")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
@Generated
public class EmailController {


   private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

        int status = emailService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(), emailRequest.getMessage());

        if (status == 200)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(status).build();
    }




}
