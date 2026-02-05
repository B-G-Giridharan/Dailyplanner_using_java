package com.example.todo.controller;

import com.example.todo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthController Tests")
class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController();
    }

    @Test
    @DisplayName("Should successfully sign up a new user")
    void testSignupSuccess() {
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password123");

        String result = authController.signup(user);

        assertEquals("SUCCESS", result);
    }

    @Test
    @DisplayName("Should return USER_EXISTS when signing up duplicate user")
    void testSignupUserExists() {
        User user1 = new User();
        user1.setUsername("testuser");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUsername("testuser");
        user2.setPassword("password2");

        authController.signup(user1);
        String result = authController.signup(user2);

        assertEquals("USER_EXISTS", result);
    }

    @Test
    @DisplayName("Should successfully login with correct credentials")
    void testLoginSuccess() {
        User signupUser = new User();
        signupUser.setUsername("loginuser");
        signupUser.setPassword("password123");
        authController.signup(signupUser);

        User loginUser = new User();
        loginUser.setUsername("loginuser");
        loginUser.setPassword("password123");

        String result = authController.login(loginUser);

        assertEquals("SUCCESS", result);
    }

    @Test
    @DisplayName("Should fail login with wrong password")
    void testLoginWrongPassword() {
        User signupUser = new User();
        signupUser.setUsername("user1");
        signupUser.setPassword("correctpass");
        authController.signup(signupUser);

        User loginUser = new User();
        loginUser.setUsername("user1");
        loginUser.setPassword("wrongpass");

        String result = authController.login(loginUser);

        assertEquals("FAIL", result);
    }

    @Test
    @DisplayName("Should fail login with non-existent user")
    void testLoginNonExistentUser() {
        User loginUser = new User();
        loginUser.setUsername("nonexistent");
        loginUser.setPassword("password");

        String result = authController.login(loginUser);

        assertEquals("FAIL", result);
    }
}
