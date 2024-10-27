package com.scm.proj.spring.services;

import java.util.List;
import java.util.Optional;

import com.scm.proj.spring.entity.User;

public interface userService {

    User savUser(User user);

    User updaUser(User user);

    Optional<User> getUserById(String id);

    void deleteUser(String id);

    List<User> getAllUser();

    boolean isUserExist(String id);

    boolean isUserExistByEmail(String email);

    //Add More Methods
    User getUserByEmail(String email);
}