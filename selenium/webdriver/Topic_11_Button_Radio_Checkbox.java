package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.support.Color;

import java.awt.*;
import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;

// test
public class Topic_11_Button_Radio_Checkbox {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();

        /*
        selenium 3x
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        */
        driver.manage().timeouts().implicitlyWait(ofSeconds(30));
        driver.manage().window().maximize();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Button() {
        // stepp 01 - truy cập trang
        driver.get("https://www.fahasa.com/customer/account/create");

        // step 02 - Navigate qua tab Dăng nhập
        driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

        sleepInSecond(3);

        // step 03 - kiểm tra nút Dăng nhập Disable
        WebElement loginButton = driver.findElement(By.cssSelector("button.fhs-btn-login"));
        Assert.assertFalse(loginButton.isEnabled());

        // step 04 - kiẻme tra button backgroud có màu xám :
        System.out.println("Color from CSS : " + Color.fromString(loginButton.getCssValue("background-color").toLowerCase()));
        System.out.println("Color in Hexa : " + Color.fromString(loginButton.getCssValue("background-color")).asHex().toLowerCase());

        Assert.assertEquals(Color.fromString(loginButton.getCssValue("background-color")).asHex().toLowerCase(), "#000000");

        // step 05 - nhạpa dữ liệu hợp lệ
        driver.findElement(By.cssSelector("input#login_username")).sendKeys("hello@gmail.com");
        driver.findElement(By.cssSelector("input#login_password")).sendKeys("Aa@123456");

        sleepInSecond(3);

        // step 06 - Dăng nhập enabled
        Assert.assertTrue(loginButton.isEnabled());

        System.out.println("Color from CSS (with input) : " + Color.fromString(loginButton.getCssValue("background-color").toLowerCase()));
        System.out.println("Color in Hexa (with input) : " + Color.fromString(loginButton.getCssValue("background-color")).asHex().toLowerCase());

        // step 07 - kiẻme tra background color màu đỏ : #C92127
        Assert.assertEquals(Color.fromString(loginButton.getCssValue("background-color")).asHex().toLowerCase(), "#c92127");
    }

    private void sleepInSecond(long sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();////
        }
    }

    @AfterClass
    public void afterClass() {
        // driver.quit();
    }
}