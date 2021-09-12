package com.jasdom.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorTest {

    EmailValidator emailValidator;

    @BeforeEach
    public void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void testContainsAt() {
        assertTrue(emailValidator.validate("hello@gmail.com"));
    }

    @Test
    public void testMissingAt() {
        assertFalse(emailValidator.validate("hellogmail.com"));
    }

    @Test
    public void testMissingDomain() {
        assertFalse(emailValidator.validate("hello@.com"));
    }

    @Test
    public void testMissingTLD() {
        assertFalse(emailValidator.validate("hello@gmail"));
    }

    @Test
    public void testBadTLD() {
        assertFalse(emailValidator.validate("hello@gmail.1"));
    }

    @Test
    public void testEmptyTLD() {
        assertFalse(emailValidator.validate("hello@gmail."));
    }

    @Test
    public void testEmpty() {
        assertFalse(emailValidator.validate(""));
    }

    @Test
    public void testTooLong() {
        assertFalse(emailValidator.validate("hello@gmail.gmail.com"));
    }

    @Test
    public void testMissingName() {
        assertFalse(emailValidator.validate("@gmail.com"));
    }

    @Test
    public void testMultipleAt() {
        assertFalse(emailValidator.validate("hello@world@gmail.com"));
    }

}