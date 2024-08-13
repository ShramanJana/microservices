package com.shraman.user.service.services;

import com.shraman.user.service.entities.User;

import java.util.List;

public interface UserService {


    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);

    User updateUser(String userId, User userData);

    User removeUser(String userId);
}
