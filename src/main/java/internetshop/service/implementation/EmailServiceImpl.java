package internetshop.service.implementation;

import internetshop.model.VerificationToken;
import internetshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException {

        Context context = new Context();
        context.setVariable("token", verificationToken);

        String process = templateEngine.process("email-template/confirm-registration", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + verificationToken.getUser().getFirstName());
        helper.setText(process, true);
        helper.setTo(verificationToken.getUser().getEmail());

        javaMailSender.send(mimeMessage);
    }
}
