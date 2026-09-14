import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
public class InspectCurrentFlow {
  public static void main(String[] args) {
    try (Playwright pw = Playwright.create()) {
      Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      Page page = browser.newPage();
      String eventTitle = "QA Summit Rahul Shetty " + System.currentTimeMillis();
      page.navigate("https://eventhub.rahulshettyacademy.com/");
      page.getByPlaceholder("you@email.com").fill("ranjanqbs@gmail.com");
      page.getByLabel("Password").fill("Ranjan@121");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
      page.getByText("Logout").waitFor();
      page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
      page.waitForLoadState(LoadState.NETWORKIDLE);
      page.locator("#event-title-input").waitFor();
      page.locator("#event-title-input").fill(eventTitle);
      page.locator("#admin-event-form textarea").fill("Rahul Shetty QA Meetups");
      page.getByLabel("Category").selectOption("Concert");
      page.getByLabel("City").fill("Test City");
      page.getByLabel("Venue").fill("Test Venue");
      page.getByLabel("Event Date & Time").fill("2026-12-18T07:25");
      page.getByLabel("Price ($)").fill("100");
      page.getByLabel("Total Seats").fill("50");
      page.locator("#add-event-btn").click();
      page.getByText("Event created!").waitFor();
      page.locator("#nav-events").click(new Locator.ClickOptions().setTimeout(10000));
      page.waitForTimeout(3000);
      Locator eventCards = page.getByTestId("event-card");
      Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(eventTitle));
      System.out.println("VISIBLE=" + targetCard.isVisible());
      String seatsText = targetCard.getByText("seats").innerText();
      System.out.println("BEFORE=" + seatsText + ", parsed=" + Integer.parseInt(seatsText.split(" ")[0]));
      targetCard.getByTestId("book-now-btn").click();
      page.getByLabel("Full Name").fill("Test Student");
      page.locator("#customer-email").fill("test.student@example.com");
      page.getByPlaceholder("+91 98765 43210").fill("9876543210");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
      page.getByText("Your tickets are reserved.").waitFor();
      page.locator("#nav-events").click();
      page.waitForTimeout(5000);
      Locator eventCardsAfterBooking = page.getByTestId("event-card");
      Locator targetCardAfterBooking = eventCardsAfterBooking.filter(new Locator.FilterOptions().setHasText(eventTitle));
      String seatsTextAfterBooking = targetCardAfterBooking.getByText("seats").innerText();
      System.out.println("AFTER=" + seatsTextAfterBooking + ", parsed=" + Integer.parseInt(seatsTextAfterBooking.split(" ")[0]));
      browser.close();
    }
  }
}
