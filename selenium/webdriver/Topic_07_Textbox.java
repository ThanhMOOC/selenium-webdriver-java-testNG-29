package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static java.time.Duration.*;

// test
public class Topic_07_Textbox {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    String email, firstname, lastname, fullname, username, pwd, employeeId;
    String passportId, issueDate, expiryDate, comment;

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

        email = randomMail();
        username = randomUsername();
        firstname = "Thanh";
        lastname = "MOOC";
        fullname = firstname + " " + lastname;
        pwd = "Automation123@";

        passportId = "135066835";
        expiryDate = "2018-04-17";
        issueDate = "2018-04-16";
        comment = "Employer passport \nIndentificatiob Number : 135066835";

        // driver.get("http://live.techpanda.org/");
    }

    @Test
    public void TC_01_textbox() {
        // step 01
        driver.get("http://live.techpanda.org/");

        // step 02
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        // step 03
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        // sử dụng explicitwait để chờ register_button
        // explicitWait.until(ExpectedConditions.visibilityOf(register_button));

        // step 04

        driver.findElement(By.cssSelector("input#firstname")).sendKeys(firstname);
        driver.findElement(By.cssSelector("input#lastname")).sendKeys(lastname);
        driver.findElement(By.cssSelector("input#email_address")).sendKeys(email);
        driver.findElement(By.cssSelector("input#password")).sendKeys(pwd);
        driver.findElement(By.cssSelector("input#confirmation")).sendKeys(pwd);

        // step 05
        driver.findElement(By.xpath("//button[@title='Register']")).click();

        // step 06
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");

        // step 07

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div[@class='box-content']/p")).getText();

        Assert.assertTrue(contactInfo.contains(fullname));
        Assert.assertTrue(contactInfo.contains(email));

        // log out
        driver.findElement(By.cssSelector("a.skip-account")).click();
        driver.findElement(By.cssSelector("a[title='Log Out']")).click();

        sleepInSecond(3);

        // log in
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        sleepInSecond(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys(email);
        driver.findElement(By.cssSelector("input#pass")).sendKeys(pwd);
        driver.findElement(By.cssSelector("button#send2")).click();

        Assert.assertTrue(contactInfo.contains(fullname));
        Assert.assertTrue(contactInfo.contains(email));

    }

    private void sleepInSecond(long sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String randomMail() {
        // dùng random để tạo mail
        return "thanh"+ new Random().nextInt(99999) + "@gmail.com";
    }

    public String randomUsername() {
        // dùng random để tạo mail
        return "thanh"+ new Random().nextInt(99999);
    }

    @AfterClass
    public void afterClass() {
        // driver.quit();
    }
}