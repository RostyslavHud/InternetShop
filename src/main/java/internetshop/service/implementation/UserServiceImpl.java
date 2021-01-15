package internetshop.service.implementation;

import internetshop.enums.Errors;
import internetshop.enums.Role;
import internetshop.model.User;
import internetshop.repository.UserRepository;
import internetshop.exception.ServiceException;
import internetshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByName(String name) throws ServiceException {
        if (name.equals("")){
            throw new ServiceException(Errors.EMPTY_USER_NAME);
        }
        return userRepository.findByName(name).orElseThrow(()->new ServiceException(Errors.USER_NOT_FOUND));
    }

    @Override
    public void addUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException(Errors.EMPTY_USER);
        }
        if (userRepository.countName(user.getName()) > 0) {
            throw new ServiceException(Errors.SAME_USER_NAME);
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(user.getPassword());
        userRepository.save(user);
    }
}
