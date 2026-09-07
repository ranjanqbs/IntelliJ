import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class uiTesting2 {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
/////////////////////////////////////////
    @BeforeMethod
    public void setUp() {
        // Create Playwright
        playwright = Playwright.create();

        // Launch Chromium browser in headed mode
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        context = browser.newContext();
        // Create page
        page = browser.newPage();

        ///////////////////
        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        //////////////////



        // Navigate to URL
        page.navigate("https://rahulshettyacademy.com/AutomationPractice//");

        // To set timeout globally assertion test forcefully
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);

    }
    ///////////////////////////////////

    @Test
    public void popupValidations ()
    {
        assertThat(page.getByPlaceholder("Hide/Show Example")).isVisible();
        page.locator("#hide-textbox").click();
        // to verify hidden part below code
        assertThat(page.getByPlaceholder("Hide/Show Example")).isHidden();
     // like thread.sleep  it wait for that fixed time on that page only not globally
    page.waitForTimeout(5000);
    System.out.println("Test");

    page.onDialog(dialog-> dialog.accept());

        page.waitForTimeout(10000);
    page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Alert")).click();


    page.waitForTimeout(10000);
        System.out.println("Test2");

        page.getByRole (AriaRole. BUTTON, new Page.GetByRoleOptions().setName("Mouse Hover")).hover();
        page.waitForTimeout(3000);
        page.getByRole (AriaRole. LINK, new Page.GetByRoleOptions().setName("Top")).click();


        FrameLocator framesPage = page.frameLocator("#courses-iframe");

            framesPage.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Learning Path")).click();

        String textCheck = framesPage.locator(".inner-box h1").textContent();

        System.out.println(textCheck);
    }

    @Test
    public void screenShots () {

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagescreenshot.png")));

        Locator displayedEditBox = page.getByPlaceholder("Hide/Show Example");

        displayedEditBox.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("editboxscreenshot.png")));

        page.locator("#hide-textbox").click();


        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("page1screenshot.png")));


    }





    @AfterMethod
    public void TearDown()

    {



        //////////////////////////////////////////////////
        // Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        /////////////////////////////////////////////////


        // Trace url
        //https://trace.playwright.dev/

        browser.close();

        System.out.println("Ranjan commit test");
    }
}