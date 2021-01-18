package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.User;
import internetshop.model.VerificationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public interface UserService {

    User findByName(String name) throws ServiceException;

    void addNewUser(User user) throws ServiceException, MessagingException;

    void confirmRegistration(String token) throws ServiceException;
}
