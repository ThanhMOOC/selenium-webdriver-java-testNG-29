package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static java.time.Duration.ofSeconds;

// test
public class Topic_09_Default_Dropdown {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    String firstname, lastname, email, company, pwd;
    String birthday, birthmonth, birthyear;

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

        firstname = "Thanh";
        lastname = "MOOC";
        email = randomMail();
        pwd = "Aa12345@!";
        company = "Company";

        birthday = "12";
        birthmonth = "May";
        birthyear = "1980";
    }

    @Test
    public void TC_01_Register() {

        // step 01 - truy cập trang
        driver.get("https://demo.nopcommerce.com/");
        sleepInSecond(5);

        // step 02 - click register trên header
        driver.findElement(By.cssSelector("a.ico-register")).click();
        sleepInSecond(5);

        // step 03 - input dữ liệu
        driver.findElement(By.id("FirstName")).sendKeys(firstname);
        driver.findElement(By.id("LastName")).sendKeys(lastname);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Company")).sendKeys(company);

        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));

        day.selectByVisibleText(birthday);
        Assert.assertEquals(day.getOptions().size(), 32);

        month.selectByVisibleText(birthmonth);
        Assert.assertEquals(month.getOptions().size(), 13);

        year.selectByVisibleText(birthyear);
        Assert.assertEquals(year.getOptions().size(), 112);

        driver.findElement(By.id("Password")).sendKeys(pwd);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(pwd);

        // step 04 - click register
        driver.findElement(By.id("register-button")).click();
        sleepInSecond(5);

        // step 05 - verify đã vào trang homepage sau khi đăng kí thành công
        Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");

        // step 06 - login, vào trang My Account để kiểm tra ngày tháng năm
        // login
        driver.findElement(By.className("ico-login")).click();
        driver.findElement(By.cssSelector("input#Email")).sendKeys(email);
        driver.findElement(By.cssSelector("input#Password")).sendKeys(pwd);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        sleepInSecond(5);

        // vào trang My Account
        driver.findElement(By.className("ico-account")).click();
        sleepInSecond(5);

        // Kiểm  tra ngày tháng năm
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(),birthday);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText(), birthmonth);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(), birthyear);

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