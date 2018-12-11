package mail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.util.concurrent.TimeUnit;


public class MainTest {
  private static final String DRIVER = "webdriver.chrome.driver";
  private static final String DRIVER_PATH = "src/test/resources/driver/chromedriver.exe";
  private static final String URL = "https://mail.tut.by/";
  private static final String USERNAME_VALUE = "testanna";
  private static final String PASSWORD_VALUE = "testanna123";
  private static final String EXPECTED_TITLE = "Яндекс.Почта";
  private static final String ADDRESS = "ms.tsukihime@mail.ru";
  private static final String ELEMENT_MAIL_SUBJECT = "input[class='mail-Compose-Field-Input-Controller js-compose-field js-editor-tabfocus-prev']";
  private static final String SUBJECT = "test";
  private static final String BODY = "test message";
  private static final String ADDRESS_XPATH = "//span[@title='ms.tsukihime@mail.ru']";
  private static final String DRAFT_MAIL_ADDRESS = "span[class='js-contact-bubble mail-Bubble-Contact js-bubble mail-Bubble']";
  private static final String DRAFT_MAIL_ADDRESS_ATTRIBUTE = "data-yabble-email";
  private static final String DRAFT_MAIL_ATTRIBUTE = "Value";
  private static final String ELEMENT_SEND_MAIL_ADDRESS = "div[class='mail-User mail-User_small js-contact-actions-dropdown js-email']";
  private static final String SEND_MAIL_ADDRESS_ATTRIBUTE = "data-email";
  private static final String EXPECTED_LOGOUT_TITLE = "Яндекс.Паспорт";

  private WebDriver driver;

  @BeforeClass
  public void start()  {
    System.setProperty(DRIVER, DRIVER_PATH);
    driver = new ChromeDriver();
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    driver.get(URL);
  }

    @Test
    public void shouldLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLogin(USERNAME_VALUE);
        loginPage.setPassword(PASSWORD_VALUE);
        loginPage.submitClick();

        Assert.assertEquals(loginPage.getTitle(), EXPECTED_TITLE);
    }

  @Test(dependsOnMethods = {"shouldLogin"})
  public void shouldCreateEmail () {
      CreatePage createPage = new CreatePage(driver);
      createPage.createClick();
      createPage.setAddress(ADDRESS);
      createPage.setMailSubject(SUBJECT);
      createPage.setMailBody(BODY);
      createPage.closeClick();
      createPage.saveClick();
  }

  @Test(dependsOnMethods = {"shouldCreateEmail"})
  public void shouldFindCreatedEmail () {
      DraftPage draftPage = new DraftPage(driver);
      draftPage.draftClick();
      draftPage.addressClick();
    String actualAddress = driver.findElement(By.cssSelector(DRAFT_MAIL_ADDRESS)).getAttribute(DRAFT_MAIL_ADDRESS_ATTRIBUTE);
    String actualSubject = driver.findElement(By.cssSelector(ELEMENT_MAIL_SUBJECT)).getAttribute(DRAFT_MAIL_ATTRIBUTE);
    Assert.assertTrue(ADDRESS.equals(actualAddress) && SUBJECT.equals(actualSubject) && driver.getPageSource().contains(BODY));

    }

  @Test(dependsOnMethods = {"shouldFindCreatedEmail"} )
  public void shouldSendEmail () {
    DraftPage draftPage = new DraftPage(driver);
    draftPage.mailSendClick();

  }

  @Test(dependsOnMethods = {"shouldSendEmail"}, expectedExceptions = NoSuchElementException.class)
  public void shouldEmailRemovedFromDrafts()  {
      driver.findElement(By.xpath(ADDRESS_XPATH));
  }

  @Test(dependsOnMethods = {"shouldEmailRemovedFromDrafts"})
  public void shouldEmailAppearedInSend () {
    SendPage sendPage = new SendPage(driver);
    sendPage.sendTabClick();
    sendPage.addressClick();
    (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ELEMENT_SEND_MAIL_ADDRESS)));
    String actualAddress = driver.findElement(By.cssSelector(ELEMENT_SEND_MAIL_ADDRESS)).getAttribute(SEND_MAIL_ADDRESS_ATTRIBUTE);

    Assert.assertTrue(ADDRESS.equals(actualAddress) && driver.getPageSource().contains(SUBJECT) && driver.getPageSource().contains(BODY));
  }

  @Test(dependsOnMethods = {"shouldEmailAppearedInSend"})
  public void shouldLogOut () {
    LogoutPage logoutPage = new LogoutPage(driver);
    logoutPage.logoutMenuClick();
    logoutPage.logoutButtonClick();
    String actualTitle = driver.getTitle();

    Assert.assertEquals(actualTitle,EXPECTED_LOGOUT_TITLE);
  }

  @AfterClass
  public void stop () {
    driver.close();
  }
}
