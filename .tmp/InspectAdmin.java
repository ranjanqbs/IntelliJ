import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
public class InspectAdmin {
  public static void main(String[] args) {
    try (Playwright pw = Playwright.create()) {
      Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      Page page = browser.newPage();
      page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
      page.waitForTimeout(3000);
      System.out.println("URL=" + page.url());
      System.out.println("TITLE=" + page.title());
      System.out.println("HAS_TITLE_INPUT=" + page.locator("#event-title-input").count());
      System.out.println("BODY_TEXT=" + page.locator("body").innerText().substring(0, Math.min(3000, page.locator("body").innerText().length())));
      browser.close();
    }
  }
}
