package webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

// test
public class Topic_17_iFrame {
    WebDriver driver;
    Actions actions;
    JavascriptExecutor javascriptExecutor;

    @BeforeClass
    public void beforeClass() {
        // driver = new FirefoxDriver();
        driver = new ChromeDriver();
        /*
        selenium 3x
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        actions = new Actions(driver);

        javascriptExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_iFrame_formsite() {
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");

        driver.findElement(By.cssSelector("div.details__form-image img")).click();

        WebElement formIframe = driver.findElement(By.cssSelector("div.details__form-template>iframe"));

        Assert.assertTrue(formIframe.isDisplayed());

        // nhay vao iframe
        driver.switchTo().frame(formIframe);

        new Select(driver.findElement(By.cssSelector("select#RESULT_RadioButton-2"))).selectByVisibleText("Sophomore");
        new Select(driver.findElement(By.cssSelector("select#RESULT_RadioButton-3"))).selectByVisibleText("West Dorm");
        driver.findElement(By.xpath("//label[text()='Male']")).click();

        driver.findElement(By.cssSelector("input.submit_button")).click();

        // nhay ra DOM
        driver.switchTo().defaultContent();

        driver.findElement(By.cssSelector("nav.header--desktop-floater a.menu-item-login")).click();

        driver.findElement(By.cssSelector("button#login")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div#message-error")).getText(), "Username and password are both required.");
    }

    @Test
    public void TC_02_iFrame_HDFC() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        WebElement iframe = driver.findElement(By.cssSelector("frame[name='login_page']"));

        driver.switchTo().frame(iframe);

        driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("thanhmooc");
        driver.findElement(By.xpath("//a[text()='CONTINUE']")).click();

        driver.switchTo().defaultContent();

        Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
        driver.findElement(By.cssSelector("input#keyboard")).sendKeys("abc123");
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