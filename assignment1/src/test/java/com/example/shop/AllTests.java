package com.example.shop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderItemTest.class,
        OrderTest.class,
        PricingServiceTest.class,
        DiscountServiceTest.class,
        PaymentValidatorTest.class,
        OrderServiceTest.class
})
public class AllTests {
}
