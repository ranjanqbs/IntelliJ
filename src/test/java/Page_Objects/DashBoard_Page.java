package Page_Objects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DashBoard_Page {

    private final Page page;

    public DashBoard_Page(Page page) {
        this.page = page;
    }

    public void waitForDashboardPageLoad() {
        assertThat(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
    }
}
