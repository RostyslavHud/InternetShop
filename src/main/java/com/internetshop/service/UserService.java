package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public interface UserService {

    User findByName(String name) throws ServiceException;

    void addNewUser(User user) throws ServiceException, MessagingException;

    void confirmRegistration(String token) throws ServiceException;

    void deleteById(Long id);
}
