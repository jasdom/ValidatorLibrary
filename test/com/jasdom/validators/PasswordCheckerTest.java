package com.jasdom.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

public class PasswordCheckerTest {

    PasswordChecker passwordChecker;

    @BeforeEach
    public void setUp() {
        passwordChecker = new PasswordChecker();
    }

    @Test
    public void testPasswordMeetsLengthRequirement() {
        passwordChecker.minLength(8);
        assertTrue(passwordChecker.validate("password"));
    }

    @Test
    public void testPasswordTooShort() {
        passwordChecker.minLength(8);
        assertFalse(passwordChecker.validate("pass"));
    }

    @Test
    public void testPasswordContainsUppercase() {
        passwordChecker.needsUppercase();
        assertTrue(passwordChecker.validate("Kormfmklsf45"));
    }

    @Test
    public void testPasswordLacksUppercase() {
        passwordChecker.needsUppercase();
        assertFalse(passwordChecker.validate("gdgsdgtssgs"));
    }

    @Test
    public void testPasswordLacksUppercaseEmpty() {
        passwordChecker.needsUppercase();
        assertFalse(passwordChecker.validate(""));
    }

    @Test
    public void testPasswordContainsSpecialCharacters() {
        passwordChecker.needsSpecialCharacters("!@#$%^&*");
        assertTrue(passwordChecker.validate("@@$&hi"));
    }

    @Test
    public void testPasswordLacksSpecialCharacters() {
        passwordChecker.needsSpecialCharacters("!@#$%^&*");
        assertFalse(passwordChecker.validate("gstystt"));
    }

    @Test
    public void testPasswordLacksSpecialCharactersEmpty() {
        passwordChecker.needsSpecialCharacters("!@#$%^&*");
        assertFalse(passwordChecker.validate(""));
    }

}
