package com.example.todo.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Model Tests")
class UserTest {

    @Test
    @DisplayName("Should create User and get/set username")
    void testUserUsername() {
        User user = new User();
        user.setUsername("testuser");
        
        assertEquals("testuser", user.getUsername());
    }

    @Test
    @DisplayName("Should create User and get/set password")
    void testUserPassword() {
        User user = new User();
        user.setPassword("password123");
        
        assertEquals("password123", user.getPassword());
    }

    @Test
    @DisplayName("Should create User with default constructor")
    void testUserDefaultConstructor() {
        User user = new User();
        
        assertNotNull(user);
    }

    @Test
    @DisplayName("Should set and get multiple properties")
    void testUserMultipleProperties() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");
        
        assertEquals("john", user.getUsername());
        assertEquals("secret", user.getPassword());
    }
}
