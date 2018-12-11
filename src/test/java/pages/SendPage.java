package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='#sent']")
    private WebElement sendTab;

    @FindBy(xpath = "//span[@title='ms.tsukihime@mail.ru']")
    private WebElement address;

    @FindBy(css="div[class='mail-User mail-User_small js-contact-actions-dropdown js-email']")
    private WebElement sendMailAddress;

    public SendPage (WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(driver, this);
    }

    public void sendTabClick () {
        sendTab.click();
    }
    public void addressClick () {
        wait.until(ExpectedConditions.visibilityOf(address));
        address.click();

    }
}
