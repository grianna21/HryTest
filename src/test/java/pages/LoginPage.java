package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver webDriver;

    @FindBy(id="Username")
    private WebElement login;

    @FindBy(id="Password")
    private WebElement password;

    @FindBy(css="input[class='button loginButton gradientforbutton']")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        this.webDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setLogin(String loginValue){
        login.sendKeys(loginValue);
    }

    public void setPassword(String passwordValue){
        password.sendKeys(passwordValue);
    }

    public void submitClick(){
        submitButton.submit();
    }

    public String getTitle(){
        return webDriver.getTitle();
    }
}
