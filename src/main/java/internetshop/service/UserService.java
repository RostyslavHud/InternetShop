package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByName(String name) throws ServiceException;
}
