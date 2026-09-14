package Test_Cases;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class uiTesting {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

// ==================== BROWSER SETUP ====================

    @BeforeMethod
    public void setup()
    {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");



        context = browser.newContext();


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


    }



// ==================== BROWSER SETUP END ====================
    @Test
    public void ChildWindowHandle()

    {
        //Page newPage= context.waitForPage(()-> blinkingTexts.first().click());
        Page newPage = context.waitForPage(() -> page.locator(".blinkingText").first().click());


        newPage.waitForLoadState();

        String childText = newPage.locator(".red").textContent();

// Example:
// "Please email us at mentor@rahulshettyacademy.com with below template..."

        String emailId = childText.split("at ")[1].split(" ")[0];

        System.out.println("Email ID: " + emailId);

        page.getByLabel("Username:").fill(emailId);

        System.out.println(
                "Entered Email: " + page.getByLabel("Username:").inputValue()
        );
    }



    @Test
    public void UIControls() {
        Locator userRdBtn = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User"));
        userRdBtn.click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Okay")).click();
        Assert.assertTrue(userRdBtn.isChecked());
        Locator checkBoxTerms = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));
        checkBoxTerms.check();
        Assert.assertTrue(checkBoxTerms.isChecked());
        page.getByRole(AriaRole.COMBOBOX).selectOption("Teacher");
        page.waitForTimeout(6000);
    }

}