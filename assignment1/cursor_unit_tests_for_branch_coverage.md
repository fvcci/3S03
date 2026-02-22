# Unit tests for branch coverage
_Exported on 2/21/2026 at 22:48:06 EST from Cursor (2.5.17)_

---

**User**

@assignment1/src/main/java/com/example/shop generate unit tests for the best branch coverage

---

**Cursor**

I'll start by exploring the source code to understand what needs to be tested.

Now let me check the project build structure.

There's no build tool configured yet. I need to set up a proper project structure with JUnit. Let me create a Maven `pom.xml` and the test files.

Now I'll create all the test files. Each test class is designed to hit every branch in the corresponding source class.

Now let me update the `.iml` file to include the test source root and run the tests.

Maven isn't installed locally. Let me try with the Maven wrapper or Gradle, or check what's available.

Let me install the Maven wrapper so we can run the tests without requiring a global Maven install.

All 39 tests pass. Here's a summary of what was created:

## Results: 39 tests, 0 failures

### Test files created

| Test Class | Tests | Branches Covered |
|---|---|---|
| **OrderItemTest** | 6 | `quantity <= 0`, `quantity < 0`, `unitPrice < 0`, `unitPrice == 0` (boundary), valid construction, `getTotalPrice` |
| **OrderTest** | 6 | `addItem` when `CREATED`, `addItem` when `PAID`, `addItem` when `CANCELLED`, `getItems`, `setStatus`, initial status |
| **PricingServiceTest** | 6 | Empty order loop (0 iterations), single item, multiple items, `subtotal < 0` throws, `subtotal == 0` returns 0, `subtotal > 0` returns 20% |
| **DiscountServiceTest** | 8 | `null` code, empty code, blank code, `STUDENT10`, `STUDENT10` lowercase (case-insensitivity), `BLACKFRIDAY`, `INVALID` throws, unrecognized default code |
| **PaymentValidatorTest** | 6 | `null` method, `"card"`, `"paypal"`, `"crypto"`, unknown method throws, uppercase (case-insensitivity) |
| **OrderServiceTest** | 7 | Invalid payment (crypto) cancels, `null` payment cancels, valid payment no discount, `STUDENT10` discount, `BLACKFRIDAY` discount, empty order with valid payment, `INVALID` discount throws |

### Branch coverage breakdown

Every `if`/`else`, `switch` case, loop zero/one/many iteration, and exception-throwing path is exercised:

- **OrderItem**: both guard clauses (`quantity <= 0`, `unitPrice < 0`) + happy path
- **Order.addItem**: `status == CREATED` (true branch) + `status != CREATED` (false branch for both `PAID` and `CANCELLED`)
- **PricingService.calculateTax**: `subtotal < 0`, `subtotal == 0`, `subtotal > 0` (all 3 branches)
- **PricingService.calculateSubtotal**: loop with 0, 1, and 2 items
- **DiscountService**: `null || blank` short-circuit, all 4 switch cases (`STUDENT10`, `BLACKFRIDAY`, `INVALID`, `default`)
- **PaymentValidator**: `null` guard, all 3 switch cases (`card`/`paypal`, `crypto`, `default`)
- **OrderService.processOrder**: invalid payment (early return) vs valid payment (full flow)

A `pom.xml` was also created with JUnit 5 so you can run these with `mvn test` from the `assignment1` directory.

---

**User**

tests sholud be public functions @assignment1/src/test/java/com/example/shop/DiscountServiceTest.java:3-5 also use these imports

---

**Cursor**

I need to update all test files to use JUnit 4 imports and make test methods/classes public.

I see that `DiscountServiceTest` already has the JUnit 4 imports but still has package-private methods. I need to switch all files to JUnit 4 imports (`org.junit.Test` / `org.junit.Assert`) and make all classes and test methods `public`. I also need to handle `assertThrows` which doesn't exist in JUnit 4 — I'll use `@Test(expected = ...)` instead.

Now I need to update the `pom.xml` to use JUnit 4 instead of JUnit 5, and add the vintage engine so Surefire picks up JUnit 4 tests.

All 39 tests pass with JUnit 4. All six test files now use `import org.junit.Test` / `import static org.junit.Assert.*`, and every test class and method is `public`. Exception-expecting tests use `@Test(expected = ...)` instead of `assertThrows`.

---

**User**

make a file that's a test suite of everything

---

**Cursor**

Created `AllTests.java` — a JUnit 4 test suite that runs all 39 tests from every test class. You can run it with:

```bash
mvn test -Dtest=com.example.shop.AllTests
```

