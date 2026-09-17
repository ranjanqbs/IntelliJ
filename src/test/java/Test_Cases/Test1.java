package Test_Cases;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test1 {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
    Path tracePath;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(true)
        );

        context = browser.newContext();
        tracePath = Paths.get("trace-test1-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId() + ".zip");

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (context != null && tracePath != null) {
                context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
            }
        } finally {
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
        }
    }

    @Test(groups = {"smoke"})
    public void DashBoardPage() {
        page.locator("#signInBtn").click();
        page.waitForLoadState();
        System.out.println("Login page opened");
    }
}