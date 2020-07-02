package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.EmailConfig;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    IUserRepository iUserRepository;

    @Override
    public void sendMail(String sendTo, String title, String content) {
        try {
            MimeMessage  mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(emailConfig.getEmailFrom());
            message.setTo(sendTo);
            message.setSubject(title);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
