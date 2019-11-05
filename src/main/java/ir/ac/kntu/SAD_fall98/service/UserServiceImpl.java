package ir.ac.kntu.SAD_fall98.service;

import ir.ac.kntu.SAD_fall98.model.User;
import ir.ac.kntu.SAD_fall98.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        System.out.println("UserService: ");
        System.out.println("before save: " + user);
        User savedUser = userRepository.save(user);
        System.out.println("after save: " + savedUser);
    }

    @Override
    public User findById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(RuntimeException::new);
    }
}
