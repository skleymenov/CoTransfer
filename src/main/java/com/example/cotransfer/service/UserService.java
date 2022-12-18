package com.example.cotransfer.service;


import com.example.cotransfer.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    void save(User user);

    void deleteUser(Long id);

    void update(User user);
}
