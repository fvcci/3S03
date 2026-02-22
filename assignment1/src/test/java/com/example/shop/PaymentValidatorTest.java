package com.example.shop;

import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentValidatorTest {

    private final PaymentValidator validator = new PaymentValidator();

    @Test
    public void returnsFalseForNullPaymentMethod() {
        assertFalse(validator.isPaymentMethodValid(null));
    }

    @Test
    public void returnsTrueForCard() {
        assertTrue(validator.isPaymentMethodValid("card"));
    }

    @Test
    public void returnsTrueForPaypal() {
        assertTrue(validator.isPaymentMethodValid("paypal"));
    }

    @Test
    public void returnsFalseForCrypto() {
        assertFalse(validator.isPaymentMethodValid("crypto"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void throwsForUnknownPaymentMethod() {
        validator.isPaymentMethodValid("bitcoin");
    }

    @Test
    public void isCaseInsensitive() {
        assertTrue(validator.isPaymentMethodValid("CARD"));
    }
}
