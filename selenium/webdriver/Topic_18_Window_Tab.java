package webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

// test
public class Topic_18_Window_Tab {
    WebDriver driver;
    Actions actions;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        // driver = new ChromeDriver();
        /*
        selenium 3x
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        actions = new Actions(driver);

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Window_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String currentID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        switchToWindowByTitle("Google");
        driver.findElement(By.cssSelector("button#W0wltc div")).click();

        sleepInSecond(1);

        switchToWindowByTitle("Selenium WebDriver");
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
        switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");

        sleepInSecond(1);

        switchToWindowByTitle("Selenium WebDriver");
        driver.findElement(By.xpath("//a[text()='TIKI']")).click();
        switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        sleepInSecond(1);

        closeAllWindow_without_parent(currentID);

        Assert.assertEquals(driver.getTitle(), "Selenium WebDriver");
    }

    @Test
    public void TC_02_Window_Tab_techPanda() {
        driver.get("http://live.techpanda.org/");
        String currentID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();

        driver.findElement(By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
        sleepInSecond(5);
        driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

        driver.findElement(By.xpath("//span[text()='Compare']")).click();

        sleepInSecond(3);

        switchToWindowByTitle("Products Comparison List - Magento Commerce");

        Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
        closeAllWindow_without_parent(currentID);

        driver.findElement(By.xpath("//a[text()='Clear All']")).click();

        sleepInSecond(3);

        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        Assert.assertEquals(alert.getText(), "Are you sure you would like to remove all products from your comparison?");
        alert.accept();

        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The comparison list was cleared.");

    }

    @Test
    public void TC_03_Window_Tab_Cambridge() {
        driver.get("https://dictionary.cambridge.org/vi/");
        String currentID = driver.getWindowHandle();

        driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();

        sleepInSecond(3);

        driver.findElement(By.cssSelector("span.cdo-login-button")).click();
        sleepInSecond(3);

        switchToWindowByTitle("Login");
        driver.manage().window().maximize();

        driver.findElement(By.cssSelector("input[value='Log in']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div.gigya-composite-control-textbox input.gigya-error~span")).getText(), "This field is required");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.gigya-composite-control-password input.gigya-error~span")).getText(), "This field is required");

        sleepInSecond(3);

        closeAllWindow_without_parent(currentID);

        driver.findElement(By.cssSelector("input#searchword")).sendKeys("automation");
        driver.findElement(By.cssSelector("button.cdo-search-button i")).click();

        sleepInSecond(3);

        // driver.findElement(By.cssSelector("button.fc-close i")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("h2.di-title")).getText(), "automation");

    }

    @Test
    public void TC_04_Window_Tab_Havard() {
        driver.get("https://courses.dce.harvard.edu/");
    }

    private void sleepInSecond(long sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    @AfterClass
    public void afterClass() {
       driver.quit();
    }
}