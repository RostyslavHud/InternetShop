package internetshop.service.implementation;

import internetshop.domain.User;
import internetshop.repository.UserRepository;
import internetshop.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User getById(long id) {
        return userRepository.findById(id).get();
    }
}
