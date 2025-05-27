package com.microservice.users.service;

import com.microservice.users.entities.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
}
