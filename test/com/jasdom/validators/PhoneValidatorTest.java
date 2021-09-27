package com.jasdom.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneValidatorTest {

    PhoneValidator phoneValidator;

    @BeforeEach
    public void setUp() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    public void testOnlyContainsDigits() {
        assertTrue(phoneValidator.validate("869421573"));
    }

    @Test
    public void testContainsSymbols() {
        assertFalse(phoneValidator.validate("86942A573"));
    }

    @Test
    public void testShortened() {
        assertTrue(phoneValidator.validate("865478947"));
    }

    @Test
    public void testFullNumber() {
        assertTrue(phoneValidator.validate("+37065478947"));
    }

    @Test
    public void testBadShortened() {
        assertFalse(phoneValidator.validate("765478947"));
    }

    @Test
    public void testSpaces() {
        assertFalse(phoneValidator.validate("     "));
    }

    @Test
    public void testEmpty() {
        assertFalse(phoneValidator.validate(""));
    }
}
