package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DraftEmailPage {

    private static final String ADDRESS_ATTRIBUTE = "data-yabble-email";
    private static final String SUBJECT_ATTRIBUTE = "Value";

    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(css="span[class='js-contact-bubble mail-Bubble-Contact js-bubble mail-Bubble']")
    private WebElement address;

    @FindBy(css="input[class='mail-Compose-Field-Input-Controller js-compose-field js-editor-tabfocus-prev']")
    private WebElement subject;

    @FindBy(css="button[class='nb-button _nb-large-action-button _init js-editor-tabfocus-next js-send nb-group-start ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']")
    private WebElement mailSend;


    public DraftEmailPage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(driver, this);
    }

    public String getAddress(){
        wait.until(ExpectedConditions.visibilityOf(address));
        return address.getAttribute(ADDRESS_ATTRIBUTE);
    }

    public String getSubject(){
        return subject.getAttribute(SUBJECT_ATTRIBUTE);
    }

    public boolean containsText(String text){
        return webDriver.getPageSource().contains(text);
    }

    public void sendMail() {
        mailSend.click();
    }

 }
