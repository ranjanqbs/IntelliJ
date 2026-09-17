package Page_Objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventHubBookingPage {

    private final Page page;

    public EventHubBookingPage(Page page) {
        this.page = page;
    }

    public void goToHome() {
        page.navigate("https://eventhub.rahulshettyacademy.com/");
    }

    public void login(String email, String password) {
        if (page.locator("input[placeholder='you@email.com']").count() > 0) {
            page.locator("input[placeholder='you@email.com']").fill(email);
            page.getByLabel("Password").fill(password);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.waitForURL("**/");
        }
    }

    public void verifyHomePageLoaded() {
        assertThat(page.locator("#nav-events")).isVisible();
    }

    public MyBookingsPage openMyBookings() {
        page.locator("#nav-bookings").click();
        page.waitForURL("**/bookings");
        return new MyBookingsPage(page);
    }

    public void ensureBookingExists() {
        page.locator("#nav-bookings").click();
        page.waitForURL("**/bookings");

        Locator bookingLinks = page.locator("a[href^='/bookings/']");
        if (bookingLinks.count() == 0) {
            page.locator("#nav-events").click();
            page.waitForURL("**/events");

            Locator eventCards = page.getByTestId("event-card");
            eventCards.first().waitFor();
            Assert.assertTrue(eventCards.count() > 0, "No events available to book.");

            Locator firstEvent = eventCards.first();
            firstEvent.getByTestId("book-now-btn").click(new Locator.ClickOptions().setTimeout(30000));

            page.getByLabel("Full Name").fill("Test Student");
            page.locator("#customer-email").fill("test.student@example.com");
            page.getByPlaceholder("+91 98765 43210").fill("9876543210");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
            assertThat(page.getByText("Your tickets are reserved.")).isVisible();

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View My Bookings")).click();
            page.waitForURL("**/bookings");
        }
    }

    public static class MyBookingsPage {
        private final Page page;

        public MyBookingsPage(Page page) {
            this.page = page;
        }

        public BookingDetailsPage openFirstBookingDetails() {
            Locator bookingLinks = page.locator("a[href^='/bookings/']");
            Assert.assertTrue(bookingLinks.count() > 0, "No bookings found under My Bookings.");

            Locator firstBookingLink = bookingLinks.first();
            String href = firstBookingLink.getAttribute("href");
            Assert.assertNotNull(href, "The first booking detail link should not be null.");

            firstBookingLink.click();
            return new BookingDetailsPage(page);
        }

        public void verifyBookingRemoved(String bookingId) {
            Locator cancelledBooking = page.locator("a[href='/bookings/" + bookingId + "']");
            Assert.assertEquals(cancelledBooking.count(), 0,
                    "The cancelled booking should no longer appear in My Bookings.");
        }
    }

    public static class BookingDetailsPage {
        private final Page page;

        public BookingDetailsPage(Page page) {
            this.page = page;
            page.waitForURL("**/bookings/*");
        }

        public String getBookingIdFromUrl() {
            String url = page.url();
            return url.substring(url.lastIndexOf('/') + 1);
        }

        public void cancelBooking() {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel Booking")).click();
            page.waitForTimeout(2000);
        }
    }
}
