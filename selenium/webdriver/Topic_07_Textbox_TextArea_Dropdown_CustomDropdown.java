package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.time.Duration.*;

// test
public class Topic_07_Textbox_TextArea_Dropdown_CustomDropdown {
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
    public void TC_01_textbox_textarea() {
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

    @Test
    public void TC_02_textbox_textarea() {

        // step 01
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        sleepInSecond(2);

        // step 02
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        sleepInSecond(2);

        // step 03
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        // step 04
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

        sleepInSecond(2);

        // step 05
        driver.findElement(By.name("firstName")).sendKeys(firstname);
        driver.findElement(By.name("lastName")).sendKeys(lastname);

        employeeId = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input")).getAttribute("value");

        driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();

        sleepInSecond(2);

        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input")).sendKeys(username);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div//input")).sendKeys(pwd);
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div//input")).sendKeys(pwd);

        // step 06
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleepInSecond(10);

        // step 07
        Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstname);
        Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastname);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input")).getAttribute("value"), employeeId);

        // step 08
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();

        // step 09
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/parent::div//button")).click();

        // step 10
        driver.findElement(By.xpath("//label[text()='Number']/parent::div//following-sibling::div//input")).sendKeys(passportId);
        driver.findElement(By.xpath("//label[text()='Issued Date']/parent::div//following-sibling::div//input")).sendKeys(issueDate);
        driver.findElement(By.xpath("//label[text()='Expiry Date']/parent::div//following-sibling::div//input")).sendKeys(expiryDate);
        driver.findElement(By.xpath("//label[text()='Comments']/parent::div//following-sibling::div//textarea")).sendKeys(comment);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        sleepInSecond(3);

        /* Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + passportId + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + issueDate + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + expiryDate + "']")).isDisplayed()); */

        // step 11
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSecond(3);

        // step 12
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div//following-sibling::div//input")).getAttribute("value"), passportId);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div//following-sibling::div//textarea")).getAttribute("value"), comment);

        // step 13
        driver.findElement(By.cssSelector("li.oxd-userdropdown")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        // step 14
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // step 15
        driver.findElement(By.xpath("//span[text()='My Info']")).click();
        sleepInSecond(5);

        // step 16
        Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), firstname);
        Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), lastname);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input")).getAttribute("value"), employeeId);

        // step 17
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSecond(3);

        // step 18
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div//following-sibling::div//input")).getAttribute("value"), passportId);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div//following-sibling::div//textarea")).getAttribute("value"), comment);
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