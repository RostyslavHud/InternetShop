package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.enums.Role;
import com.internetshop.mysqlModel.User;
import com.internetshop.mysqlModel.UserAttempts;
import com.internetshop.mysqlModel.VerificationToken;
import com.internetshop.mysqlRepository.UserAttemptsRepository;
import com.internetshop.mysqlRepository.UserRepository;
import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlRepository.VerificationTokenRepository;
import com.internetshop.service.LanguageService;
import com.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;


@Service("userService")
public class UserServiceImpl implements UserService {

    private static final int MAX_ATTEMPTS = 4;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttemptsRepository userAttemptsRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private LanguageService languageService;


    @Override
    @Cacheable(value = "user", key = "#name")
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
        myToken.setExpiryDate(user.getRegistrationDate());
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
        verificationToken.getUser().setAccountNonLocked(true);
        userRepository.save(verificationToken.getUser());

        verificationTokenRepository.delete(verificationToken);

        userAttemptsRepository.save(newUserAttempts(verificationToken.getUser().getName()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void resetFailAttempts(String username) {
        UserAttempts userAttempts = userAttemptsRepository.findByUsername(username)
                .orElse(newUserAttempts(username));
        userAttempts.setAttempts(0);
        userAttempts.setLastModified(LocalDateTime.now());
        userAttemptsRepository.save(userAttempts);
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void updateFailAttempts(String username) {
        UserAttempts userAttempts = userAttemptsRepository.findByUsername(username)
                .orElse(newUserAttempts(username));
        int attempt = userAttempts.getAttempts() + 1;

        if (attempt > MAX_ATTEMPTS) {
            User user = userRepository.findByName(username);
            user.setAccountNonLocked(false);
            userRepository.save(user);
        }

        userAttempts.setAttempts(attempt);
        userAttempts.setLastModified(LocalDateTime.now());
        userAttemptsRepository.save(userAttempts);
    }

    @Override
    public void resetUser(User user) throws ServiceException, MessagingException {
        if (userRepository.countEmail(user.getEmail()) < 1) {
            throw new ServiceException(Errors.INCORRECT_USER_EMAIL);
        }

        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, userRepository.findByEmail(user.getEmail()));
        myToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        verificationTokenRepository.save(myToken);

        emailService.resetUserMail(myToken);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public void resetPassword(String token, User user) throws ServiceException {
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
        verificationToken.getUser().setAccountNonLocked(true);
        verificationToken.getUser().setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(verificationToken.getUser());

        verificationTokenRepository.delete(verificationToken);

        resetFailAttempts(verificationToken.getUser().getName());
    }

    @Override
    public UserAttempts getUserAttempts(String username) throws ServiceException {
        return userAttemptsRepository.findByUsername(username).
                orElseThrow(() -> new ServiceException(Errors.USER_ATTEMPTS_NOT_FOUND));
    }

    private UserAttempts newUserAttempts(String username) {
        UserAttempts userAttempts = new UserAttempts();
        userAttempts.setAttempts(0);
        userAttempts.setUsername(username);
        userAttempts.setLastModified(LocalDateTime.now());
        return userAttempts;
    }
}
