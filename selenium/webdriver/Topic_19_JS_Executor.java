package webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

// test
public class Topic_19_JS_Executor {
    WebDriver driver;

    JavascriptExecutor jsExecutor;


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();

        jsExecutor = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_JavaScript_Executor() {

        navigateToUrlByJS("http://live.techpanda.org/");

        String techPandDomain = (String) executeForBrowser("return document.domain");
        Assert.assertEquals(techPandDomain, "live.techpanda.org");

        String techPandaUrl = (String) executeForBrowser("return document.URL");
        Assert.assertEquals(techPandaUrl, "http://live.techpanda.org/");

        hightlightElement("//a[text()='Mobile']");
        clickToElementByJS("//a[text()='Mobile']");

        hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
        clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");

        Assert.assertTrue(isExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));

        hightlightElement("//a[text()='Customer Service']");
        clickToElementByJS("//a[text()='Customer Service']");

        String customerServiceTitle = (String) executeForBrowser("return document.title");
        Assert.assertEquals(customerServiceTitle, "Customer Service");

        scrollToBottomPage();

        sendkeyToElementByJS("//input[@type='email']", "mail1235454353453453453455@gmail.com");
        clickToElementByJS("//span[text()='Subscribe']");

        sleepInSecond(5);

        Assert.assertTrue(isExpectedTextInInnerText("Thank you for your subscription."));

        navigateToUrlByJS("https://www.facebook.com/");
        Assert.assertEquals((String) executeForBrowser("return document.domain"), "facebook.com");

    }

    @Test
    public void TC_02_Verify_HTML5_validation_message() {
        navigateToUrlByJS("https://login.ubuntu.com/");
        sendkeyToElementByJS("//input[@class='textType']", "a");
        clickToElementByJS("//span[text()='Log in']");

        Assert.assertEquals(getElementValidationMessage("//input[@class='textType']"), "Please include an '@' in the email address. 'a' is missing an '@'.");

        navigateToUrlByJS("https://sieuthimaymocthietbi.com/account/register");
        clickToElementByJS("//button[text()='Đăng ký']");
        Assert.assertEquals(getElementValidationMessage("//input[@id='lastName']"), "Please fill out this field.");
    }

    public void switchToWindowByTitle(String title) {
        Set<String> allIDs = driver.getWindowHandles();

        for (String id:allIDs) {
            driver.switchTo().window(id);

            String actualTitle = driver.getTitle();
            if (actualTitle.equals(title)) {
                break;
            }
        }
    }

    public void closeAllWindow_without_parent(String parentID) {
        Set<String> allIDs = driver.getWindowHandles();

        for (String id:allIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public Object executeForBrowser(String javaScript) {
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean isExpectedTextInInnerText(String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage() {
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(String url) {
        jsExecutor.executeScript("window.location = '" + url + "'");
        sleepInSecond(3);
    }

    public void hightlightElement(String locator) {
        WebElement element = getElement(locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(String locator) {
        jsExecutor.executeScript("arguments[0].click();", getElement(locator));
        sleepInSecond(3);
    }

    public void scrollToElementOnTop(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void scrollToElementOnDown(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
    }

    public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue +"');", getElement(locator));
    }

    public void removeAttributeInDOM(String locator, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
    }

    public void sendkeyToElementByJS(String locator, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
    }

    public String getAttributeInDOM(String locator, String attributeName) {
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(locator));
    }

    public String getElementValidationMessage(String locator) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
    }

    public boolean isImageLoaded(String locator) {
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
        return status;
    }

    public WebElement getElement(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    private void sleepInSecond(long sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass() {
       driver.quit();
    }
}