package internetshop.service.implementation;

import internetshop.model.User;
import internetshop.repository.UserRepository;
import internetshop.exception.ServiceException;
import internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByName(String name) throws ServiceException {
        if (name.equals("")){
            throw new ServiceException("Name can't be empty");
        }
        return userRepository.findByName(name);
    }
}
