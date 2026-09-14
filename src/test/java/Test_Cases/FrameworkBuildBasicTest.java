package Test_Cases;

import Page_Objects.AdminEvent_Page;

import Page_Objects.DashBoard_Page;
import Page_Objects.LoginPage_RahulSetty;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FrameworkBuildBasicTest extends BaseClass {

    @Test(description = "Create an event")
    public void DemoTest() {
        String eventTitle = "QA Summit Rahul Shetty " + System.currentTimeMillis();

        LoginPage_RahulSetty loginpage = new LoginPage_RahulSetty(page, base_url);
        loginpage.loginToApplication();

        DashBoard_Page dashBoardPage = new DashBoard_Page(page);
        dashBoardPage.waitForDashboardPageLoad();

        AdminEvent_Page adminEventPage = new AdminEvent_Page(page);
        adminEventPage.goTo();

        page.getByText("Logout").waitFor();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.locator("#event-title-input").waitFor();
        adminEventPage.createEvent(eventTitle, "Rahul Shetty QA Meetups", "Concert", "Test City", "Test Venue", "2026-12-18T07:25", "100", "50");

        page.locator("#nav-events").click(new Locator.ClickOptions().setTimeout(10000));
        page.waitForTimeout(3000);

        Locator eventCards = page.getByTestId("event-card");
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(eventTitle));
        assertThat(targetCard).isVisible();

        String seatsText = targetCard.getByText("seats").innerText();
        int seatsNumBeforeBooking = Integer.parseInt(seatsText.split(" ")[0]);

        targetCard.getByTestId("book-now-btn").click();
        page.getByLabel("Full Name").fill("Test Student");
        page.locator("#customer-email").fill("test.student@example.com");
        page.getByPlaceholder("+91 98765 43210").fill("9876543210");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();

        PlaywrightAssertions.assertThat(page.getByText("Your tickets are reserved.")).isVisible();

        String bookingRef = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View My Bookings")).click();

        Locator bookingCards = page.locator("#booking-card");
        Locator targetBookingCard = bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));
        assertThat(targetBookingCard).isVisible();

        page.locator("#nav-events").click();
        page.waitForTimeout(3000);

        Locator eventCardsAfterBooking = page.getByTestId("event-card");
        Locator targetCardAfterBooking = eventCardsAfterBooking.filter(new Locator.FilterOptions().setHasText(eventTitle));
        String seatsTextAfterBooking = targetCardAfterBooking.getByText("seats").innerText();
        int seatsNumAfterBooking = Integer.parseInt(seatsTextAfterBooking.split(" ")[0]);
        Assert.assertTrue(seatsNumBeforeBooking > seatsNumAfterBooking);
    }

    @AfterMethod
    public void tearDown() {
        browser.close();
    }
}