package Test_Cases;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    Playwright playwright;
    Browser browser;
    Page page;
    String base_url;




    @BeforeMethod
    public void setUp() throws IOException {

        Properties prop= new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");

        // Create Playwright
        playwright = Playwright.create();

        if (browserName.equalsIgnoreCase("chrome")) {
            // to open on system chrome app
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }







        // Create page
        page = browser.newPage();

        base_url = prop.getProperty("qa.base_url");


        // To set timeout globally assertion test forcefully
        PlaywrightAssertions.setDefaultAssertionTimeout(10000);

    }
}
