import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
public class InspectSite {
  public static void main(String[] args) {
    try (Playwright pw = Playwright.create()) {
      Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      Page page = browser.newPage();
      page.navigate("https://eventhub.rahulshettyacademy.com/");
      System.out.println("URL1=" + page.url());
      System.out.println("TITLE1=" + page.title());
      System.out.println("CONTENT1=" + page.content().substring(0, Math.min(2000, page.content().length())));
      page.getByPlaceholder("you@email.com").fill("ranjanqbs@gmail.com");
      page.getByLabel("Password").fill("Ranjan@121");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
      page.waitForTimeout(5000);
      System.out.println("URL2=" + page.url());
      System.out.println("TITLE2=" + page.title());
      System.out.println("CONTENT2=" + page.content().substring(0, Math.min(2000, page.content().length())));
      page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("C:/Users/K_Ran/IdeaProjects/PlaywrightFramework/.tmp/site-shot.png")));
      browser.close();
    }
  }
}
