package webdriver;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v122.network.Network;
import org.openqa.selenium.devtools.v122.network.model.Headers;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.time.Duration.ofSeconds;

// test
public class Topic_13_Accept_Alert {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    WebDriverWait explicitWait;

    String projectLocation = System.getProperty("user.dir");

    @BeforeClass
    public void beforeClass() {
        // driver = new FirefoxDriver();
        driver = new ChromeDriver();

        /*
        selenium 3x
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        */
        driver.manage().timeouts().implicitlyWait(ofSeconds(30));
        driver.manage().window().maximize();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }

    @Test
    public void TC_07_JS_Alert() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02 - click "Click for JS Alert"
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // step 03 - kieerm tra message
        Assert.assertEquals(alert.getText(), "I am a JS Alert");

        alert.accept();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
    }

    @Test
    public void TC_08_Confirm_Alert() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02 - click "Click for JS Confirm"
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // step 03 - kieerm tra message
        Assert.assertEquals(alert.getText(), "I am a JS Confirm");

        alert.dismiss();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
    }

    @Test
    public void TC_09_Prompt_Alert() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02 - click "Click for JS Confirm"
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // step 03 - kieerm tra message
        Assert.assertEquals(alert.getText(), "I am a JS prompt");

        alert.sendKeys("daominhdam");
        alert.accept();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: daominhdam");
    }

    @Test
    public void TC_11_Authentification_Alert() throws IOException {
        // Strick ByPass
        // step 01 - truy cập trang + ByPass để truyền user/pwd vào URL
        // driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        // step 02 - kiểm tra message
        // Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        // Dùng AutoIT
        // thực thi file autoIT để chờ Alert
        Runtime.getRuntime().exec(new String[] {projectLocation + "\\autoIT\\authen_firefox.exe", "admin", "admin"} );

        sleepInSecond(3);
        // step 01 - truy cập trang
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    @Test
    public void TC_12_DevTool() {

        // Get DevTool object
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        // Start new session
        devTools.createSession();
        // Enable the Network domain of devtools

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        // Encode username/ password
        Map<String, Object> headers = new HashMap<String, Object>();
        String basicAuthen = "Basic " + new String (new Base64().encode(String.format("%s:%s", "admin", "admin").getBytes()));
        headers.put("Authorization", basicAuthen);
        // Set to Header
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());
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
        driver.quit();
    }
}