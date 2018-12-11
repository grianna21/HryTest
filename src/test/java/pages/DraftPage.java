package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DraftPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='#draft']")
    private WebElement draftTab;

    @FindBy(xpath = "//span[@title='ms.tsukihime@mail.ru']")
    private WebElement address;

    @FindBy(css="span[class='js-contact-bubble mail-Bubble-Contact js-bubble mail-Bubble']")
    private WebElement draftMailAddress;

   @FindBy(css="button[class='nb-button _nb-large-action-button _init js-editor-tabfocus-next js-send nb-group-start ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']")
   private WebElement mailSend;


    public DraftPage (WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(driver, this);
    }
        public void draftClick () {
        draftTab.click();
        webDriver.navigate().refresh();
    }
    public void addressClick () {
        wait.until(ExpectedConditions.visibilityOf(address));
        address.click();
        wait.until(ExpectedConditions.visibilityOf(draftMailAddress));
    }
    public void mailSendClick () {
        mailSend.click();
    }

 }
