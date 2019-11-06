package ir.ac.kntu.service;

import ir.ac.kntu.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    User findById(long userId);
}
