package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendMailPage {

    private static final String ADDRESS_ATTRIBUTE = "data-email";
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(css="div[class='mail-User mail-User_small js-contact-actions-dropdown js-email']")
    private WebElement address;


    public SendMailPage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(driver, this);
    }

    public String getAddress() {
        wait.until(ExpectedConditions.visibilityOf(address));
        return address.getAttribute(ADDRESS_ATTRIBUTE);
    }

    public boolean containsSubject(String subject){
        return webDriver.getPageSource().contains(subject);
    }

    public boolean containsText(String text){
        return webDriver.getPageSource().contains(text);
    }
}
