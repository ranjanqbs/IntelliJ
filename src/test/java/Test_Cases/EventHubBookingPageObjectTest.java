package Test_Cases;

import Page_Objects.EventHubBookingPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class EventHubBookingPageObjectTest extends BaseClass {

    @Test(description = "Verify booking can be viewed and cancelled from the My Bookings flow")
    public void cancelFirstBookingFromDetailsPage() {
        EventHubBookingPage bookingPage = new EventHubBookingPage(page);

        bookingPage.goToHome();
        bookingPage.login("ranjanqbs@gmail.com", "Ranjan@121");
        bookingPage.verifyHomePageLoaded();

        bookingPage.ensureBookingExists();
        EventHubBookingPage.MyBookingsPage myBookingsPage = bookingPage.openMyBookings();
        EventHubBookingPage.BookingDetailsPage bookingDetailsPage = myBookingsPage.openFirstBookingDetails();
        String bookingId = bookingDetailsPage.getBookingIdFromUrl();

        bookingDetailsPage.cancelBooking();

        page.locator("#nav-bookings").click();
        page.waitForURL("**/bookings");
        myBookingsPage.verifyBookingRemoved(bookingId);
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
