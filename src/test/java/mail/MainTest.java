package mail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class MainTest {
  private static final String DRIVER = "webdriver.chrome.driver";
  private static final String DRIVER_PATH = "src/test/resources/driver/chromedriver.exe";
  private static final String URL = "https://mail.tut.by/";
  private static final String USERNAME_ELEMENT = "Username";
  private static final String USERNAME_VALUE = "testanna";
  private static final String PASSWORD_ELEMENT = "Password";
  private static final String PASSWORD_VALUE = "testanna123";
  private static final String SUBMIT = "input[class='button loginButton gradientforbutton']";
  private static final String EXPECTED_TITLE = "Яндекс.Почта";
  private static final String MAIL_CREATE = "span[class='mail-ComposeButton-Text']";
  private static final String ELEMENT_MAIL_ADDRESS = "div[class='js-compose-field mail-Bubbles']";
  private static final String ADDRESS = "ms.tsukihime@mail.ru";
  private static final String ELEMENT_MAIL_SUBJECT = "input[class='mail-Compose-Field-Input-Controller js-compose-field js-editor-tabfocus-prev']";
  private static final String SUBJECT = "test";
  private static final String ELEMENT_MAIL_BODY = "div[class='cke_wysiwyg_div cke_reset cke_enable_context_menu cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']";
  private static final String BODY = "test message";
  private static final String CLOSE = "svg[class='svgicon svgicon-mail--Close']";
  private static final String SAVE = "button[class=' nb-button _nb-small-action-button _init nb-with-s-right-gap js-resolve']";
  private static final String ELEMENT_DRAFT_TAB = "//a[@href='#draft']";
  private static final String ADDRESS_XPATH = "//span[@title='ms.tsukihime@mail.ru']";
  private static final String DRAFT_MAIL_ADDRESS = "span[class='js-contact-bubble mail-Bubble-Contact js-bubble mail-Bubble']";
  private static final String DRAFT_MAIL_ADDRESS_ATTRIBUTE = "data-yabble-email";
  private static final String DRAFT_MAIL_ATTRIBUTE = "Value";
  private static final String MAIL_SEND = "button[class='nb-button _nb-large-action-button _init js-editor-tabfocus-next js-send nb-group-start ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']";
  private static final String ELEMENT_SEND_TAB = "//a[@href='#sent']";
  private static final String ELEMENT_SEND_MAIL_ADDRESS = "div[class='mail-User mail-User_small js-contact-actions-dropdown js-email']";
  private static final String SEND_MAIL_ADDRESS_ATTRIBUTE = "data-email";
  private static final String ELEMENT_LOGOUT_MENU = "div[class='mail-User-Name']";
  private static final String ELEMENT_LOGOUT_BUTTON = "div[class='b-mail-dropdown__item']";
  private static final String EXPECTED_LOGOUT_TITLE = "Яндекс.Паспорт";

  private WebDriver driver;

  @BeforeClass
  public void start()  {
    System.setProperty(DRIVER, DRIVER_PATH);
    driver = new ChromeDriver();
    driver.get(URL);
  }

  @Test
  public void shouldLogin() {
    driver.findElement(By.id(USERNAME_ELEMENT)).sendKeys(USERNAME_VALUE);
    driver.findElement(By.id(PASSWORD_ELEMENT)).sendKeys(PASSWORD_VALUE);
    driver.findElement(By.cssSelector(SUBMIT)).submit();
    String actualTitle = driver.getTitle();

    Assert.assertEquals(actualTitle, EXPECTED_TITLE);
  }

  @Test(dependsOnMethods = {"shouldLogin"})
  public void shouldCreateEmail () throws InterruptedException {
    Thread.sleep(2000);
    driver.findElement(By.cssSelector(MAIL_CREATE)).click();
    Thread.sleep(2000);
    driver.findElement(By.cssSelector(ELEMENT_MAIL_ADDRESS)).sendKeys(ADDRESS);
    driver.findElement(By.cssSelector(ELEMENT_MAIL_SUBJECT)).sendKeys(SUBJECT);
    driver.findElement(By.cssSelector(ELEMENT_MAIL_BODY)).sendKeys(BODY);
    driver.findElement(By.cssSelector(CLOSE)).click();
    driver.findElement(By.cssSelector(SAVE)).click();

  }

  @Test(dependsOnMethods = {"shouldCreateEmail"})
  public void shouldFindCreatedEmail () throws InterruptedException {
    driver.findElement(By.xpath(ELEMENT_DRAFT_TAB)).click();
    driver.navigate().refresh();
    Thread.sleep(4000);
    driver.findElement(By.xpath(ADDRESS_XPATH)).click();
    Thread.sleep(2000);
    String actualAddress = driver.findElement(By.cssSelector(DRAFT_MAIL_ADDRESS)).getAttribute(DRAFT_MAIL_ADDRESS_ATTRIBUTE);
    String actualSubject = driver.findElement(By.cssSelector(ELEMENT_MAIL_SUBJECT)).getAttribute(DRAFT_MAIL_ATTRIBUTE);

    Assert.assertTrue(ADDRESS.equals(actualAddress) && SUBJECT.equals(actualSubject) && driver.getPageSource().contains(BODY));
  }

  @Test(dependsOnMethods = {"shouldFindCreatedEmail"} )
  public void shouldSendEmail () {
    driver.findElement(By.cssSelector(MAIL_SEND)).click();
  }

  @Test(dependsOnMethods = {"shouldSendEmail"}, expectedExceptions = NoSuchElementException.class)
  public void shouldEmailRemovedFromDrafts () throws InterruptedException {
    Thread.sleep(5000);
    driver.findElement(By.xpath(ADDRESS_XPATH));
  }

  @Test(dependsOnMethods = {"shouldEmailRemovedFromDrafts"})
  public void shouldEmailAppearedInSend () throws InterruptedException {
    driver.findElement(By.xpath(ELEMENT_SEND_TAB)).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath(ADDRESS_XPATH)).click();
    Thread.sleep(2000);
    String actualAddress = driver.findElement(By.cssSelector(ELEMENT_SEND_MAIL_ADDRESS)).getAttribute(SEND_MAIL_ADDRESS_ATTRIBUTE);

    Assert.assertTrue(ADDRESS.equals(actualAddress) && driver.getPageSource().contains(SUBJECT) && driver.getPageSource().contains(BODY));

  }

  @Test(dependsOnMethods = {"shouldEmailAppearedInSend"})
  public void shouldLogOut () {
    driver.findElement(By.cssSelector(ELEMENT_LOGOUT_MENU)).click();
    driver.findElement(By.cssSelector(ELEMENT_LOGOUT_BUTTON)).click();
    String actualTitle = driver.getTitle();

    Assert.assertEquals(actualTitle,EXPECTED_LOGOUT_TITLE);
  }

  @AfterClass
  public void stop () {
    driver.close();
  }
}
