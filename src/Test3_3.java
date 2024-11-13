import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Test3_3 {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:automationName", "Appium");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice"); // emulator-5554
        capabilities.setCapability("appium:platformVersion", "8.0");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", "C:/JavaAppiumAutomation/JavaAppiumAutomation/apks/org.wikipedia_50492_apps.evozi.com.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // /wd/hub
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCompareArticleTitle() {

        elementToClickSkip(By.xpath("//*[contains(@text,'Skip')]"));


        waitForElementAndClick(By.xpath("//*[contains(@text,'Search')]"), "Не нашли строки ввода", 5);
        waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search')]"), "One Piece", "Не смогли написать в строку", 5);
        driver.hideKeyboard();
        assertElementVisible(By.id("org.wikipedia:id/page_list_item_title"), "Статьи не видны");
        listOnePiece(By.id("org.wikipedia:id/page_list_item_title"));
        waitElementAndClear(By.id("org.wikipedia:id/search_src_text"), "не отчистили строку ввода", 5);
        waitForElementAndClick(By.id("Navigate up"), "не нажал на стрелку", 5);
        waitForElementNotPresent(By.id("Navigate up"), "не вышли на главную страницу", 5);


    }

    private void elementToClickSkip(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage("Элемент не найден" + "\n");
        WebElement skip = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        skip.click();
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitElementAndClear(By by, String errorMassage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMassage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementVisible(By by, String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 1));
    }

    private void listOnePiece(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        List<WebElement> searchItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        for (int i = 0; i < searchItems.size(); i++) {
            String popa = searchItems.get(i).getText();
            Assert.assertTrue("Элемант под номером " + i + " не содержит нужный текст", popa.contains("Java"));
        }

    }


}
