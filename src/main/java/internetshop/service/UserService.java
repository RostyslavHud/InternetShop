package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    User findByName(String name) throws ServiceException;

    void addUser(User user) throws ServiceException;
}
