package internetshop.service.implementation;

import internetshop.model.User;
import internetshop.repository.UserRepository;
import internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
