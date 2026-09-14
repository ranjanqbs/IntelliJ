import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
public class InspectLoggedIn {
  public static void main(String[] args) {
    try (Playwright pw = Playwright.create()) {
      Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      Page page = browser.newPage();
      page.navigate("https://eventhub.rahulshettyacademy.com/");
      page.getByPlaceholder("you@email.com").fill("ranjanqbs@gmail.com");
      page.getByLabel("Password").fill("Ranjan@121");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
      page.waitForTimeout(5000);
      System.out.println("URL_AFTER_LOGIN=" + page.url());
      System.out.println("HAS_ADMIN_MENU=" + page.getByText("Admin").count());
      System.out.println("NAV_TEXT=" + page.locator("nav").innerText().substring(0, Math.min(1500, page.locator("nav").innerText().length())));
      page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
      page.waitForTimeout(5000);
      System.out.println("URL_AFTER_ADMIN_NAV=" + page.url());
      System.out.println("ADMIN_BODY=" + page.locator("body").innerText().substring(0, Math.min(2000, page.locator("body").innerText().length())));
      browser.close();
    }
  }
}
