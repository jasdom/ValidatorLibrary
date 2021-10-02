package com.jasdom.validators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {
    PhoneValidator phoneValidator;

    @BeforeEach
    void setUp() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    void notAllNumbersTest() {
        assertFalse(phoneValidator.validatePhoneNumber("A68612312"));
    }

    @Test
    void numberForConversionInvalidLengthTest() {
        assertFalse(phoneValidator.validatePhoneNumber("8686123125"));
    }

    @Test
    void invalidPhoneNumberLengthTest() {
        assertFalse(phoneValidator.validatePhoneNumber("+370686123125"));
    }

    @Test
    void unsupportedPhoneNumberPrefixTest() {
        assertFalse(phoneValidator.validatePhoneNumber("+9368612312"));
    }

    @Test
    void invalidNullPhoneNumberStringTest() {
        assertFalse(phoneValidator.validatePhoneNumber(null));
    }

    @Test
    void invalidBlankPhoneNumberStringTest() {
        assertFalse(phoneValidator.validatePhoneNumber("        "));
    }

    @Test
    void invalidEmptyPhoneNumberStringTest() {
        assertFalse(phoneValidator.validatePhoneNumber(""));
    }

    @Test
    void validNumberConversionTest() {
        assertTrue(phoneValidator.validatePhoneNumber("868612312"));
    }

    @Test
    void validPhoneNumberLesothoTest() {
        assertTrue(phoneValidator.validatePhoneNumber("+26668612312"));
    }

    @Test
    void validPhoneNumberMaldivesTest() {
        assertTrue(phoneValidator.validatePhoneNumber("+9606861231"));
    }

    @AfterEach
    void tearDown() {

    }
}
