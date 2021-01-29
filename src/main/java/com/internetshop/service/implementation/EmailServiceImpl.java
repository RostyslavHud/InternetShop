package com.internetshop.service.implementation;

import com.internetshop.mysqlModel.VerificationToken;
import com.internetshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${url}")
    String url;


    @Async
    @Override
    public void sendConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException {

        String language = verificationToken.getUser().getLanguage().getName();
        Context context = new Context(new Locale(language));
        context.setVariable("token", verificationToken);
        StringBuilder formatUrl = new StringBuilder();
        context.setVariable("tokenUrl", formatUrl.append(url).append("/confirm/")
                .append(verificationToken.getToken()).toString());

        String process = templateEngine.process("email-template/confirm-registration", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("text.welcome" + verificationToken.getUser().getFirstName());
        helper.setText(process, true);
        helper.setTo(verificationToken.getUser().getEmail());

        javaMailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void remindAboutConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException {

        String language = verificationToken.getUser().getLanguage().getName();
        Context context = new Context(new Locale(language));
        context.setVariable("token", verificationToken);
        StringBuilder formatUrl = new StringBuilder();
        context.setVariable("tokenUrl", formatUrl.append(url).append("/confirm/")
                .append(verificationToken.getToken()).toString());
        StringBuilder formatTime = new StringBuilder();
        context.setVariable("date", formatTime.append(verificationToken.getExpiryDate().getHour()).append(":")
                .append(verificationToken.getExpiryDate().getMinute()));

        String process = templateEngine.process("email-template/remind-confirm-registration", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("text.welcome" + verificationToken.getUser().getFirstName());
        helper.setText(process, true);
        helper.setTo(verificationToken.getUser().getEmail());

        javaMailSender.send(mimeMessage);
    }
}
