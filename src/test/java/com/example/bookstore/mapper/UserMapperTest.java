package com.example.bookstore.mapper;

import com.example.bookstore.dto.response.UserResponseDto;
import com.example.bookstore.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void toUserResponseDto_shouldReturnUserResponseDto_whenValidUserProvided() {
        // given
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setActive(true);

        // when
        UserResponseDto result = userMapper.toUserResponseDto(user);

        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.isActive(), result.isActive());
    }

    @Test
    void toUserResponseDto_shouldHandleNullFieldsGracefully() {
        // given
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setActive(true);

        // when
        UserResponseDto result = userMapper.toUserResponseDto(user);

        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void toUserResponseDto_shouldReturnNull_whenNullUserProvided() {
        // when
        UserResponseDto result = userMapper.toUserResponseDto(null);

        // then
        assertNull(result);
    }
}

