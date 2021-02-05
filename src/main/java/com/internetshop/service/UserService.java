package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.User;
import com.internetshop.mysqlModel.UserAttempts;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public interface UserService {

    User findByName(String name) throws ServiceException;

    void addNewUser(User user) throws ServiceException, MessagingException;

    void confirmRegistration(String token) throws ServiceException;

    void deleteById(Long id);

    void resetFailAttempts(String username);

    void updateFailAttempts(String username);

    void resetUser(User user) throws ServiceException, MessagingException;

    void resetPassword(String token, User user) throws ServiceException;

    UserAttempts getUserAttempts(String username) throws ServiceException;
}
