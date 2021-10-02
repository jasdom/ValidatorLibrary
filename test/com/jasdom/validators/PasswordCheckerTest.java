package com.jasdom.validators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordCheckerTest {
    PasswordChecker passwordChecker;

    @BeforeEach
    void setUp(){
        passwordChecker = new PasswordChecker();
    }

    @Test
    void insufficientPasswordLengthTest() {
        assertFalse(passwordChecker.validatePassword("Laba."));
    }

    @Test
    void noUppercaseLetterTest() {
        assertFalse(passwordChecker.validatePassword("labas"));
    }

    @Test
    void doesNotContainSpecialSymbolTest() {
        assertFalse(passwordChecker.validatePassword("Labas"));
    }

    @Test
    void specialSymbolNotInListTest() {
        assertFalse(passwordChecker.validatePassword("Lab~s"));
    }

    @Test
    void invalidNullPasswordStringTest() {
        assertFalse(passwordChecker.validatePassword(null));
    }

    @Test
    void invalidBlankPasswordStringTest() {
        assertFalse(passwordChecker.validatePassword("         "));
    }

    @Test
    void invalidEmptyPasswordStringTest() {
        assertFalse(passwordChecker.validatePassword(""));
    }

    @Test
    void validPasswordTest() {
        assertTrue(passwordChecker.validatePassword("Labas."));
    }

    @AfterEach
    void tearDown() {

    }
}