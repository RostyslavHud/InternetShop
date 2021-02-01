package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.enums.Role;
import com.internetshop.mysqlModel.User;
import com.internetshop.mysqlModel.VerificationToken;
import com.internetshop.mysqlRepository.UserRepository;
import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlRepository.VerificationTokenRepository;
import com.internetshop.service.LanguageService;
import com.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private LanguageService languageService;

    @Override
    public User findByName(String name) throws ServiceException {
        if (name.isEmpty()) {
            throw new ServiceException(Errors.EMPTY_USER_NAME);
        }
        return userRepository.findByName(name);
    }

    @Override
    public void addNewUser(User user) throws ServiceException, MessagingException {
        if (userRepository.countName(user.getName()) > 0) {
            throw new ServiceException(Errors.SAME_USER_NAME);
        }
        if (userRepository.countEmail(user.getEmail()) > 0) {
            throw new ServiceException(Errors.SAME_USER_EMAIL);
        }

        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        user.setLanguage(languageService.findByName(locale.toLanguageTag()));
        user.setRole(Role.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);

        emailService.sendConfirmRegistrationMail(myToken);
    }

    @Override
    public void confirmRegistration(String token) throws ServiceException {
        if (token == null) {
            throw new ServiceException(Errors.EMPTY_TOKEN);
        }
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new ServiceException(Errors.INCORRECT_TOKEN);
        }
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ServiceException(Errors.EXPIRY_DATE_TOKEN);
        }
        verificationToken.getUser().setActive(true);
        userRepository.save(verificationToken.getUser());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
