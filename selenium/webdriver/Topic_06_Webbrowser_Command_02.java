package webdriver;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;

// test
public class Topic_06_Webbrowser_Command_02 {
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
    public void TC_01_Displayed() {

        // step 01
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02
        if (driver.findElement(By.id("mail")).isDisplayed()) {
            System.out.println("Email textbox is displayed");
            driver.findElement(By.id("mail")).sendKeys("Automation Testing");
        } else {
            System.out.println("Email textbox is NOT displayed");
        }

        if (driver.findElement(By.id("under_18")).isDisplayed()) {
            System.out.println("Age Under 18 is displayed");
            driver.findElement(By.id("under_18")).click();
        } else {
            System.out.println("Age Under 18 is NOT displayed");
        }

        if (driver.findElement(By.id("edu")).isDisplayed()) {
            System.out.println("Education textbox is displayed");
            driver.findElement(By.id("edu")).sendKeys("Automation Testing");
        } else {
            System.out.println("Education textbox is NOT displayed");
        }

        // step 03
        if (driver.findElement(By.xpath("//h5[text()='Name: User5']")).isDisplayed()) {
            System.out.println("User 5 is displayed");
        } else {
            System.out.println("User 5 is NOT displayed");
        }
    }

    @Test
    public void TC_02_Enabled() {

        // step 01
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02
        if (driver.findElement(By.id("mail")).isEnabled()) {
            System.out.println("Email textbox is enabled");
        } else {
            System.out.println("Email textbox is disabled");
        }

        if (driver.findElement(By.id("under_18")).isEnabled()) {
            System.out.println("Age Under 18 is enabled");
        } else {
            System.out.println("Age Under 18 is disabled");
        }

        if (driver.findElement(By.id("edu")).isEnabled()) {
            System.out.println("Education textbox is enabled");
        } else {
            System.out.println("Education textbox is disabled");
        }

        // step 03
        if (driver.findElement(By.id("disable_password")).isEnabled()) {
            System.out.println("Password textbox is enabled");
        } else {
            System.out.println("Password textbox is disabled");
        }

        if (driver.findElement(By.id("radio-disabled")).isEnabled()) {
            System.out.println("Radio button is enabled");
        } else {
            System.out.println("Radio button is disabled");
        }

        if (driver.findElement(By.id("bio")).isEnabled()) {
            System.out.println("Biography textbox is enabled");
        } else {
            System.out.println("Biography textbox is disabled");
        }

        if (driver.findElement(By.id("job3")).isEnabled()) {
            System.out.println("Job 3 dropdown is enabled");
        } else {
            System.out.println("Job 3 dropdown is disabled");
        }

        if (driver.findElement(By.id("check-disbaled")).isEnabled()) {
            System.out.println("Interests checkbox is enabled");
        } else {
            System.out.println("Interests checkbox is disabled");
        }

        if (driver.findElement(By.id("slider-2")).isEnabled()) {
            System.out.println("Slider 2 is enabled");
        } else {
            System.out.println("Slider 2 is disabled");
        }
    }

    @Test
    public void TC_03_Selected() {

        // step 01
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // step 02
        driver.findElement(By.id("under_18")).click();
        driver.findElement(By.id("java")).click();

        // step 03
        Assert.assertTrue(driver.findElement(By.id("under_18")).isSelected());
        Assert.assertTrue(driver.findElement(By.id("java")).isSelected());

        // step 04
        driver.findElement(By.id("under_18")).click();
        driver.findElement(By.id("java")).click();

        //step 05
        Assert.assertTrue(driver.findElement(By.id("under_18")).isSelected());
        Assert.assertFalse(driver.findElement(By.id("java")).isSelected());

    }

    @Test
    public void TC_04_Register_Mail_Chimp() {
        driver.get("https://login.mailchimp.com/signup/");
        sleepInSecond(2);
        driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        // verify sign-up button is enabled
        Assert.assertTrue(driver.findElement(By.id("create-account-enabled")).isEnabled());

        // verify error msg
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='email']/following-sibling::span")).getText(),
                "An email address must contain a single @.");
        Assert.assertEquals(
                driver.findElement(By.xpath("//input[@id='new_username']/following-sibling::span")).getText(),
                "Please enter a value");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']/span")).getText(),
                "One lowercase character");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']/span")).getText(),
                "One uppercase character");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='number-char not-completed']/span")).getText(),
                "One number");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='special-char not-completed']/span")).getText(),
                "One special character");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='8-char not-completed']/span")).getText(),
                "8 characters minimum");

        driver.findElement(By.id("email")).sendKeys("mooctacthanh@gmail.com");

        WebElement element = driver.findElement(By.cssSelector("button#create-account-enabled"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        // jsExecutor.executeScript("argument[0].scrollIntoView(true);",element);

        // case 1 - only number
        driver.findElement(By.id("new_password")).sendKeys("123");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        // case 2 - only lower
        driver.findElement(By.id("new_password")).clear();
        driver.findElement(By.id("new_password")).sendKeys("abc");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        // case 3 - only upper
        driver.findElement(By.id("new_password")).clear();
        driver.findElement(By.id("new_password")).sendKeys("ABC");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        // case 4 - only special character
        driver.findElement(By.id("new_password")).clear();
        driver.findElement(By.id("new_password")).sendKeys("*&@");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        // case 5 - more than 7 chars
        driver.findElement(By.id("new_password")).clear();
        driver.findElement(By.id("new_password")).sendKeys("12345678");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());

        // case 6 - correct password
        driver.findElement(By.id("new_password")).clear();
        driver.findElement(By.id("new_password")).sendKeys("ABCabc1234&@");
        // driver.findElement(By.id("create-account-enabled")).click();
        sleepInSecond(2);

        Assert.assertFalse(driver.findElement(By.cssSelector("li.number-char.completed")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li.special-char.completed")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());

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