package internetshop.services.implementation;

import internetshop.domain.User;
import internetshop.repositories.UserRepository;
import internetshop.services.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User getById(long id) {
        return userRepository.findById(id).get();
    }
}
