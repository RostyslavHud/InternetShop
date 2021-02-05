package com.internetshop.service;

import com.internetshop.mysqlModel.VerificationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    void sendConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException;

    void remindAboutConfirmRegistrationMail(VerificationToken verificationToken) throws MessagingException;

    void resetUserMail(VerificationToken verificationToken) throws MessagingException;
}
