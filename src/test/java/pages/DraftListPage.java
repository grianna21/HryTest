package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DraftListPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[@href='#draft']")
    private WebElement draftTab;

    @FindBy(xpath = "//span[@title='ms.tsukihime@mail.ru']")
    private WebElement address;


    public DraftListPage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, 10);
        PageFactory.initElements(driver, this);
    }

    public void openDraftTab () {
        draftTab.click();
        webDriver.navigate().refresh();
    }
    public void findEmailByAddress () {
        wait.until(ExpectedConditions.visibilityOf(address));
        address.click();
    }


 }
