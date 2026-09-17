package Page_Objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;


//Constructor for page object model
public class LoginPage_RahulSetty {

    //Page link = "https://rahulshettyacademy.com/loginpagePractise/";

    Page page;
    String base_url;
    String email_placeholder="you@email.com";
    private static final String password_placeholder="Password";

    public  LoginPage_RahulSetty(Page page, String base_url)
    {
        this.page = page;
        this.base_url = base_url;
    }

public void loginToApplication() {
 
    // Use the instance fields initialized in the constructor
    this.page.navigate(this.base_url);
 
    this.page.getByPlaceholder(email_placeholder).fill("ranjanqbs@gmail.com");
    this.page.getByLabel(password_placeholder).fill("Ranjan@121");
   this.page.locator("#login-btn").click(new Locator.ClickOptions().setTimeout(60000));
   this.page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(60000));
 
}}
