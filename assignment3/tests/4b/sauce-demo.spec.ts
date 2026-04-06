// TODO
import { test, expect, Page } from "@playwright/test";

const USER_DETAILS = [
  {
    username: "standard_user",
    canLogin: true,
    canCheckout: true,
    canComplete: true,
  },
  {
    username: "performance_glitch_user",
    canLogin: true,
    canCheckout: true,
    canComplete: true,
  },
  {
    username: "visual_user",
    canLogin: true,
    canCheckout: true,
    canComplete: true,
  },
  {
    username: "locked_out_user",
    canLogin: false,
    canCheckout: false,
    canComplete: false,
  },
  // ONE OF THESE TWO CANT FILL OUT THE FORM BUT CAN CONTINUE??
  {
    username: "problem_user",
    canLogin: true,
    canCheckout: false,
    canComplete: false,
  },
  {
    username: "error_user",
    canLogin: true,
    canCheckout: true,
    canComplete: false, // The last-name field input is broken, so the user cannot complete the purchase.
  },
];

export async function userLogIn(page: Page, username: string) {
  await page.goto("https://www.saucedemo.com/");
  await page.locator('[data-test="username"]').fill(username);
  await page.locator('[data-test="password"]').fill("secret_sauce");
  await page.locator('[data-test="login-button"]').click();
}

test.describe("Happy path tests", () => {
  for (const user of USER_DETAILS) {
    test.describe(`${user.username}: happy path`, () => {
      test("can login", async ({ page }) => {
        test.fail(
          !user.canLogin,
          `${user.username} is expected to fail the login test`,
        );
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);
      });

      test("can add item to cart", async ({ page }) => {
        // Precondition: user is logged in
        test.fail(!user.canLogin);
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);

        // Add item to cart
        await page
          .locator('[data-test="add-to-cart-sauce-labs-backpack"]')
          .click();
        await expect(
          page.locator('[data-test="shopping-cart-badge"]'),
        ).toHaveText("1");
      });

      test("can get to checkout step one", async ({ page }) => {
        // Precondition: user is logged in and has item in cart
        test.fail(!user.canLogin);
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);
        await page
          .locator('[data-test="add-to-cart-sauce-labs-backpack"]')
          .click();
        await expect(
          page.locator('[data-test="shopping-cart-badge"]'),
        ).toHaveText("1");

        // Checkout
        await page.locator('[data-test="shopping-cart-link"]').click();
        await page.locator('[data-test="checkout"]').click();
        expect(page).toHaveURL(/checkout-step-one/);
      });

      test("can fill out checkout information and get to checkout step two", async ({
        page,
      }) => {
        // Precondition: user is logged in and has item in cart and is on checkout step one
        test.fail(!(user.canLogin && user.canCheckout));
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);
        await page
          .locator('[data-test="add-to-cart-sauce-labs-backpack"]')
          .click();
        await expect(
          page.locator('[data-test="shopping-cart-badge"]'),
        ).toHaveText("1");
        await page.locator('[data-test="shopping-cart-link"]').click();
        await page.locator('[data-test="checkout"]').click();
        await expect(page).toHaveURL(/checkout-step-one/);

        // Fill in checkout information
        await page.locator('[data-test="firstName"]').fill("Playwright");
        await page.locator('[data-test="lastName"]').fill("Test");
        await page.locator('[data-test="postalCode"]').fill("A1A1A1");
        await page.locator('[data-test="continue"]').click();
        await expect(page).toHaveURL(/checkout-step-two/);
      });

      test("can complete purchase", async ({ page }) => {
        // Precondition: user is logged in and has item in cart and is on checkout step one
        test.fail(!(user.canLogin && user.canCheckout && user.canComplete));
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);
        await page
          .locator('[data-test="add-to-cart-sauce-labs-backpack"]')
          .click();
        await expect(
          page.locator('[data-test="shopping-cart-badge"]'),
        ).toHaveText("1");
        await page.locator('[data-test="shopping-cart-link"]').click();
        await page.locator('[data-test="checkout"]').click();
        await expect(page).toHaveURL(/checkout-step-one/);

        // Fill in checkout information
        await page.locator('[data-test="firstName"]').fill("Playwright");
        await page.locator('[data-test="lastName"]').fill("Test");
        await page.locator('[data-test="postalCode"]').fill("A1A1A1");
        await page.locator('[data-test="continue"]').click();
        await expect(page).toHaveURL(/checkout-step-two/);

        // Finish purchase
        await page.locator('[data-test="finish"]').click();
        await expect(page).toHaveURL(/checkout-complete/);
        await expect(page.locator('[data-test="complete-header"]')).toHaveText(
          "Thank you for your order!",
        );
      });

      test("can logout", async ({ page }) => {
        // Precondition: user is logged in
        test.fail(!user.canLogin);
        await userLogIn(page, user.username);
        await expect(page).toHaveURL(/inventory/);

        // Logout
        await page.getByRole("button", { name: "Open Menu" }).click();
        await page.locator('[data-test="logout-sidebar-link"]').click();
        await expect(page).toHaveURL("https://www.saucedemo.com/");
      });
    });
  }
});
