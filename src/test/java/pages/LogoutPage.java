package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage {
    private WebDriver webDriver;

    @FindBy(css = "div[class='mail-User-Name']")
    private WebElement logout;

    @FindBy(css = "div[class='b-mail-dropdown__item']")
    private WebElement logoutButton;

    public LogoutPage (WebDriver driver) {
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logoutMenuClick () {
        logout.click();
    }

    public void logoutButtonClick () {
        logoutButton.click();
    }
}
