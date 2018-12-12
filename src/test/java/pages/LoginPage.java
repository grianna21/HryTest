package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Page {

    @FindBy(id="Username")
    private WebElement login;

    @FindBy(id="Password")
    private WebElement password;

    @FindBy(css="input[class='button loginButton gradientforbutton']")
    private WebElement submitButton;

    public LoginPage(WebDriver driver, WebDriverWait wait, String login, String password){
        super(driver, wait);
        PageFactory.initElements(driver, this);
        this.login.sendKeys(login);
        this.password.sendKeys(password);
    }

    public CreateMailPage login(){
        submitButton.submit();
        return new CreateMailPage(getWebDriver(), getWait());
    }
}
