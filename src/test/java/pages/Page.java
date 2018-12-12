package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

 abstract class Page {
    private WebDriver webDriver;
    private WebDriverWait wait;

    Page(WebDriver webDriver, WebDriverWait wait){
        this.webDriver = webDriver;
        this.wait = wait;
    }

     public WebDriver getWebDriver() {
         return webDriver;
     }

     public WebDriverWait getWait() {
         return wait;
     }
 }
