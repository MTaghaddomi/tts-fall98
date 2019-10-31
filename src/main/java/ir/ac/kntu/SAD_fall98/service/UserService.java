package ir.ac.kntu.SAD_fall98.service;

import ir.ac.kntu.SAD_fall98.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User user);

    User findById(long userId);
}
