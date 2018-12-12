package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateMailPage extends Page {

    @FindBy(css="span[class='mail-ComposeButton-Text']")
    private WebElement createButton;

    @FindBy(css="div[class='js-compose-field mail-Bubbles']")
    private WebElement mailAddress;

    @FindBy(css="input[class='mail-Compose-Field-Input-Controller js-compose-field js-editor-tabfocus-prev']")
    private WebElement mailSubject;

    @FindBy(css="div[class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")
    private WebElement mailBody;

    @FindBy(css="svg[class='svgicon svgicon-mail--Close']")
    private WebElement closeButton;

    @FindBy(css="button[class=' nb-button _nb-small-action-button _init nb-with-s-right-gap js-resolve']")
    private WebElement saveButton;

    public CreateMailPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void create() {
        getWait().until(ExpectedConditions.visibilityOf(createButton));
        createButton.click();
    }
    public void setAddress(String mailAddressValue){
        getWait().until(ExpectedConditions.visibilityOf(mailAddress));
        mailAddress.sendKeys(mailAddressValue);
    }

    public void setMailSubject (String mailSubjectValue) {
        mailSubject.sendKeys(mailSubjectValue);
    }

    public void setMailBody (String mailBodyValue) {
        mailBody.sendKeys(mailBodyValue);
    }

    public void close() {
        closeButton.click();
    }

    public void save() {
        saveButton.click();
    }

    public String getTitle(){
        return getWebDriver().getTitle();
    }
    }

