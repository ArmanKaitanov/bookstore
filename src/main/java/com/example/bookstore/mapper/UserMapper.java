package com.example.bookstore.mapper;

import com.example.bookstore.dto.response.UserResponseDto;
import com.example.bookstore.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toUserResponseDto(User user) {
        if(user == null) {
            return null;
        }

        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActive()
        );
    }
}
