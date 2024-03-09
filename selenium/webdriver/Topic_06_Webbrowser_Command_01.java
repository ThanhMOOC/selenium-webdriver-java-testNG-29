package webdriver;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

// test
public class Topic_06_Webbrowser_Command_01 {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Verify_URL() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        sleepInSecond(3);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC_02_Verify_Title() {

        driver.get("http://live.techpanda.org/index.php/customer/account/login/");

        sleepInSecond(3);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC_03_Navigate_Function() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

        driver.navigate().back();

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        driver.navigate().forward();

        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC_04_Get_Page_Source_Code() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        sleepInSecond(3);

        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
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