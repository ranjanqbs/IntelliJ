package Test_Cases;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class Test1 {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

// ==================== BROWSER SETUP ====================

    @BeforeMethod(alwaysRun = true)
    public void setup()
    {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        context = browser.newContext();
        String traceFileName = "trace-test1-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId() + ".zip";

        ///////////////////
        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        //////////////////

        page = context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");

    }

    @AfterMethod(alwaysRun = true)
    public void TearDown()

    {

        //////////////////////////////////////////////////
        // Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace-test1-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId() + ".zip")));
        /////////////////////////////////////////////////

        // Trace url
        //https://trace.playwright.dev/

        browser.close();
        playwright.close();

    }



    // ==================== BROWSER SETUP END ====================
    @Test(groups = {"smoke"})
    public void DashBoardPage() {
        page.locator("#signInBtn").click();
        page.waitForLoadState();
        System.out.println("Login page opened");
    }
}