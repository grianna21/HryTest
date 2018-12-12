package mail;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private static final String SUBJECT = "test";
    private static final String BODY = "test message";
    private static final String EXPECTED_LOGOUT_TITLE = "Яндекс.Паспорт";

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
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
    public void shouldCreateEmail() {
        CreateMailPage createPage = new CreateMailPage(driver);
        createPage.createClick();
        createPage.setAddress(ADDRESS);
        createPage.setMailSubject(SUBJECT);
        createPage.setMailBody(BODY);
        createPage.closeClick();
        createPage.saveClick();
    }

    @Test(dependsOnMethods = {"shouldCreateEmail"})
    public void shouldFindCreatedEmail() {
        DraftListPage draftListPage = new DraftListPage(driver);
        draftListPage.openDraftTab();
        draftListPage.findEmailByAddress();

        DraftEmailPage draftPage = new DraftEmailPage(driver);

        Assert.assertTrue(ADDRESS.equals(draftPage.getAddress())
                && SUBJECT.equals(draftPage.getSubject()) && draftPage.containsText(BODY));
    }

    @Test(dependsOnMethods = {"shouldFindCreatedEmail"})
    public void shouldSendEmail() {
        DraftEmailPage draftPage = new DraftEmailPage(driver);
        draftPage.sendMail();
    }

    @Test(dependsOnMethods = {"shouldSendEmail"}, expectedExceptions = TimeoutException.class)
    public void shouldEmailRemovedFromDrafts() {
        DraftListPage draftListPage = new DraftListPage(driver);
        draftListPage.findEmailByAddress();
    }

    @Test(dependsOnMethods = {"shouldEmailRemovedFromDrafts"})
    public void shouldEmailAppearedInSend() {
        SendListPage sendListPage = new SendListPage(driver);
        sendListPage.openSendTab();
        sendListPage.findByAddress();

        SendMailPage sendPage = new SendMailPage(driver);

        Assert.assertTrue(ADDRESS.equals(sendPage.getAddress()) && sendPage.containsSubject(SUBJECT) && sendPage.containsText(BODY));
    }

    @Test(dependsOnMethods = {"shouldEmailAppearedInSend"})
    public void shouldLogOut() {
        LogoutPage logoutPage = new LogoutPage(driver);
        logoutPage.logoutMenuClick();
        logoutPage.logoutButtonClick();

        Assert.assertEquals(logoutPage.getTitle(), EXPECTED_LOGOUT_TITLE);
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }
}
