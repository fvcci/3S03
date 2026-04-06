import { test, expect } from "@playwright/test";

test.describe("Entry Ad Modal Page Tests", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("https://the-internet.herokuapp.com/entry_ad");
  });

  test("should show modal on initial page load", async ({ page }) => {
    await expect(page.locator("#modal")).toBeVisible();
    await expect(
      page.getByRole("heading", { name: "This is a modal window" }),
    ).toBeVisible();
  });

  test("should have close button on modal", async ({ page }) => {
    await expect(page.getByText("Close", { exact: true })).toBeVisible();
  });

  test("should close modal when clicking Close button", async ({ page }) => {
    await page.getByText("Close", { exact: true }).click();
    await expect(page.locator("#modal")).not.toBeVisible();
  });

  test("should not show modal after it has been closed and page reloaded", async ({
    page,
  }) => {
    await page.getByText("Close", { exact: true }).click();
    await expect(page.locator("#modal")).not.toBeVisible();

    await page.reload();
    await expect(page.locator("#modal")).not.toBeVisible();
  });
});
