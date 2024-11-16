package com.example.bookstore.service;


import com.example.bookstore.dto.request.RegisterUserRequestDto;
import com.example.bookstore.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(UUID id);

    UUID saveUser(RegisterUserRequestDto dto);

    User updateUser(UUID id, User userDetails);

    void deleteUser(UUID id);
}

