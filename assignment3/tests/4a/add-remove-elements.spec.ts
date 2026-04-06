import { test, expect } from "@playwright/test";

test.describe("Add/Remove Elements Page Tests", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("https://the-internet.herokuapp.com/add_remove_elements/");
  });

  test("should add one element correctly", async ({ page }) => {
    await page.getByRole("button", { name: "Add Element" }).click();
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(1);
  });

  test("should add multiple elements correctly", async ({ page }) => {
    await page.getByRole("button", { name: "Add Element" }).click();
    await page.getByRole("button", { name: "Add Element" }).click();
    await page.getByRole("button", { name: "Add Element" }).click();
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(3);
  });

  test("should remove one element correctly", async ({ page }) => {
    await page.getByRole("button", { name: "Add Element" }).click();
    await page.getByRole("button", { name: "Delete" }).first().click();
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(0);
  });

  test("should remove multiple elements correctly", async ({ page }) => {
    await page.getByRole("button", { name: "Add Element" }).click();
    await page.getByRole("button", { name: "Add Element" }).click();
    await page.getByRole("button", { name: "Add Element" }).click();

    await page.getByRole("button", { name: "Delete" }).first().click();
    await page.getByRole("button", { name: "Delete" }).first().click();

    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(1);
  });

  test("should not have delete buttons with 0 elements", async ({ page }) => {
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(0);
  });

  test("should be able to handle a large number of elements", async ({
    page,
  }) => {
    const count = 50;
    for (let i = 0; i < count; i++) {
      await page.getByRole("button", { name: "Add Element" }).click();
    }
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(
      count,
    );

    for (let i = 0; i < count; i++) {
      await page.getByRole("button", { name: "Delete" }).first().click();
    }
    await expect(page.getByRole("button", { name: "Delete" })).toHaveCount(0);
  });
});
