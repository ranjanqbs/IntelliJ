import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

//import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasicTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setUp()
    {
        // Create Playwright
        playwright = Playwright.create();

        // Launch Chromium browser in headed mode
        //Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // to open on system chrome app
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        // Create page
        page = browser.newPage();

        // Navigate to URL
        page.navigate("https://eventhub.rahulshettyacademy.com/");

        // To set timeout globally assertion test forcefully
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);

    }
    @Test(description = "Create an event")
    public void DemoTest() {



        // Print page title
        //System.out.println(page.title());

        // Below "AssertThat(page).hasTitle(" title name);  is code to verify title of page
        // Below "AssertThat(page).hasTitle(" title name);  is code to verify title of page
        //assertThat(page).hasTitle("EventHub — Discover & Book Events");

        page.getByPlaceholder("you@email.com").fill("ranjanqbs@gmail.com");
        page.getByLabel("Password").fill("Ranjan@121");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"))
                .click();

        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");

        page.locator("#event-title-input").fill("QA Summit Rahul Shetty");

        page.locator("#admin-event-form textarea").fill("Rahul Shetty QA Meetups");

        page.getByLabel("Category").selectOption("Concert");

        page.getByLabel("City").fill("Test City");

        page.getByLabel("Venue").fill("Test Venue");

        page.getByLabel("Event Date & Time").fill("2026-12-18T07:25");

        //page.waitForTimeout(3000);

        // Step 1 - Create event

        page.getByLabel("Price ($)").fill("100");

        page.getByLabel("Total Seats").fill("50");

        page.locator("#add-event-btn").click();

        assertThat(page.getByText("Event created!")).isVisible();

      // Step 2 - Find newly created event
        // in this below code custom time out fot this line only
        page.locator("#nav-events").click(new Locator.ClickOptions().setTimeout(10000));
        page.waitForTimeout(3000);

        Locator eventCards = page.getByTestId("event-card");

        System.out.println(eventCards.count());
       //Visibility of the card which we have added
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText("QA Summit Rahul Shetty"));
                assertThat(targetCard).isVisible();

        String seatsText = targetCard.getByText("seats").innerText() ;
        System.out.println(seatsText);



        int seatsNumBeforeBooking = Integer.parseInt(seatsText.split(" ")[0]);

        targetCard.getByTestId("book-now-btn"). click();

        page.getByLabel("Full Name").fill("Test Student");

        page.locator("#customer-email").fill("test.student@example.com");

        page.getByPlaceholder("+91 98765 43210").fill("9876543210");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();

// Verify confirmation message
        assertThat(page.getByText("Your tickets are reserved.")).isVisible();

// Get booking reference
        String bookingRef = page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.LINK, new Page. GetByRoleOptions(). setName ("View My Bookings")).click();

// Verify in Booking History

        Locator bookingCards = page.locator("#booking-card");

        Locator targetBookingCard = bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));

        assertThat(targetBookingCard).isVisible();

        // Seat count priductions check

        page.locator("#nav-events").click();

        Locator eventCardsAfterBooking = page.getByTestId("event-card"); //floc, Loc2, loc3, Loc4)
       //Visibility of the card which we have added
        Locator targetCardAfterBooking = eventCardsAfterBooking.filter(new Locator.FilterOptions().setHasText("QA Summit Rahul Shetty"));
        String seatsTextAfterBooking = targetCardAfterBooking.getByText("seats"). innerText();
        System.out.println(seatsTextAfterBooking);
// Afterbeekings & BeforeBookings
// 46 Seats Available

        int seatsNumAfterBooking = Integer.parseInt(seatsTextAfterBooking.split(" ")[0]);
        Assert.assertTrue(seatsNumBeforeBooking > seatsNumAfterBooking);

    }

    @AfterMethod
    public void tearDown()
    {
        browser.close();
    }




}