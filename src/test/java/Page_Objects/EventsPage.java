package Page_Objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {

   private final Page page;

   public EventsPage(Page page) {
       this.page = page;
   }

   public void goTo() {
       page.locator("#nav-events").click(new Locator.ClickOptions().setTimeout(10000));
   }

   public Locator waitForEventsPageLoad() {
       Locator eventCards = page.getByTestId("event-card");
       eventCards.first().waitFor();
       assertThat(eventCards.first()).isVisible();
       return eventCards;
   }

   public Locator findEventCards(String eventTitle) {
       Locator eventCards = waitForEventsPageLoad();
       Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(eventTitle));
       assertThat(targetCard).isVisible();
       return targetCard;
   }

   public int getSeatsCount(String eventTitle) {
       Locator targetCard = findEventCards(eventTitle);
       String seatsText = targetCard.getByText("seats").innerText();
       System.out.println(seatsText);
       return Integer.parseInt(seatsText.split(" ")[0]);
   }

   public BookingFormPage proceedToBookingEvent(String titleCard) {
       Locator targetCard = findEventCards(titleCard);
       targetCard.getByTestId("book-now-btn").click();
       return new BookingFormPage();
   }
}
