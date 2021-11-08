package com.jasdom.user_module.user_module.service;

import com.jasdom.user_module.user_module.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    private UserValidator validator = new UserValidator();

    @Test
    public void testBadFirstName(){
        User user = new User("", "Last name", "test@gmail.com", "+37064781234", "Te-st123154", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("Name"));
    }

    @Test
    public void testBadLastName(){
        User user = new User("First name", "", "test@gmail.com", "+37064781234", "Te-st123154", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("Surname"));
    }

    @Test
    public void testBadEmail(){
        User user = new User("First name", "Last name", "", "+37064781234", "Te-st123154", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("email address"));
    }

    @Test
    public void testBadPhoneNumber(){
        User user = new User("First name", "Last name", "test@gmail.com", "", "Te-st123154", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("phone number"));
    }

    @Test
    public void testBadPassword(){
        User user = new User("First name", "Last name", "test@gmail.com", "+37064781234", "", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("Password"));
    }

    @Test
    public void testBadAddress(){
        User user = new User("First name", "Last name", "test@gmail.com", "+37064781234", "", "");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("Address"));
    }

    @Test
    public void testBadEverything(){
        User user = new User("", "", "", "", "", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertFalse(response.success);
        assertTrue(response.message.contains("Name"));
        assertTrue(response.message.contains("Surname"));
        assertTrue(response.message.contains("email address"));
        assertTrue(response.message.contains("phone number"));
        assertTrue(response.message.contains("Password"));
    }

    @Test
    public void testGood(){
        User user = new User("First name", "Last name", "test@gmail.com", "+37064781234", "Te-st123154", "Vilnius");

        ServiceResponse<User> response = validator.validateUser(user);
        assertTrue(response.success);
        assertTrue(response.message.isEmpty());
    }
}
