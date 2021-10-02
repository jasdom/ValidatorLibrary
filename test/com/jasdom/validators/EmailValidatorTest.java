package com.jasdom.validators;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    void noAtSymbolTest() {
        assertFalse(emailValidator.validateEmail("jonasgmail.com"));
    }

    @Test
    void illegalAtSymbolTest() {
        assertFalse(emailValidator.validateEmail("jon@as@gmail.com"));
    }

    @Test
    void invalidLocalPartBeginningTest() {
        assertFalse(emailValidator.validateEmail(".jonas@gmail.com"));
    }

    @Test
    void invalidLocalPartEndTest() {
        assertFalse(emailValidator.validateEmail("jonas.@gmail.com"));
    }

    @Test
    void invalidLocalPartSymbolTest() {
        assertFalse(emailValidator.validateEmail("jon,as@gmail.com"));
    }

    @Test
    void localPartConsecutiveDotsTest() {
        assertFalse(emailValidator.validateEmail("jo..nas@gmail.com"));
    }

    @Test
    void invalidDomainBeginningTest() {
        assertFalse(emailValidator.validateEmail("jonas@!gmail.com"));
    }

    @Test
    void invalidDomainEndTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail!.com"));
    }

    @Test
    void invalidTLDBeginningTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.1com"));
    }

    @Test
    void invalidTLDEndTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.com/"));
    }

    @Test
    void invalidTLDLengthTooShortTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.c"));
    }

    @Test
    void invalidTLDLengthTooBigTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.cccccccccccccccccccccccccccc" +
                "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                "cccccccccccccccccccccccccccccccccccccccccccccccccccc"));
    }

    @Test
    void invalidDomainSymbolTest() {
        assertFalse(emailValidator.validateEmail("jonas@gm?ail.com"));
    }

    @Test
    void consecutiveHyphensOrDotsInDomainTest() {
        assertFalse(emailValidator.validateEmail("jonas@gm..ail.com"));
    }

    @Test
    void invalidMaxDNSLabelLengthTest() {
        assertFalse(emailValidator.validateEmail("jonas@gma.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.il.com"));
    }

    @Test
    void invalidTLDSymbolTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.c?om"));
    }

    @Test
    void consecutiveHyphensInTLDTest() {
        assertFalse(emailValidator.validateEmail("jonas@gmail.c--om"));
    }

    @Test
    void invalidNullEmailStringTest() {
        assertFalse(emailValidator.validateEmail(null));
    }

    @Test
    void invalidBlankEmailStringTest() {
        assertFalse(emailValidator.validateEmail("       "));
    }

    @Test
    void invalidEmptyEmailStringTest() {
        assertFalse(emailValidator.validateEmail(""));
    }

    @Test
    void validEmailTest() {
        assertTrue(emailValidator.validateEmail("jonas@gmail.com"));
    }

    @AfterEach
    void tearDown() {

    }
}