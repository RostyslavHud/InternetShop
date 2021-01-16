package internetshop.service;

import internetshop.model.VerificationToken;

import javax.mail.MessagingException;

public interface EmailService {

    void sendConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException;
}
