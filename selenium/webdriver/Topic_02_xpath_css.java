package webdriver;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_02_xpath_css {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        // dùn trong BeforeClass rồi thì không cần dùng ở TC nữa
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    }

    @Test
    public void TC_01_Register_Empty_Data() {
        // Action
        driver.findElement(By.id("txtFirstname")).sendKeys("");
        driver.findElement(By.id("txtEmail")).sendKeys("");
        driver.findElement(By.id("txtCEmail")).sendKeys("");
        driver.findElement(By.id("txtPassword")).sendKeys("");
        driver.findElement(By.id("txtCPassword")).sendKeys("");
        driver.findElement(By.id("txtPhone")).sendKeys("");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_02_Register_Invalid_Email() {
        // Action
        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("mooctacthanh");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        // driver.findElement(By.cssSelector("input#form#frmLogin button.btn_pink_sm")).click();
        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(), "Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }
    @Test
    public void TC_03_Register_Incorrect_Email() {
        // Action
        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("mooctacthanh248@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        // driver.findElement(By.cssSelector("input#form#frmLogin button.btn_pink_sm")).click();
        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(), "Email nhập lại không đúng");
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_04_Register_Password_Less_Than_6_Chars() {
        // Action
        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("111");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        // driver.findElement(By.cssSelector("input#form#frmLogin button.btn_pink_sm")).click();
        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(), "Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_05_Register_Incorrect_Password() {
        // Action
        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("111111");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("222222");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        // driver.findElement(By.cssSelector("input#form#frmLogin button.btn_pink_sm")).click();
        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtFirstname-error")).getText(), "Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(), "Mật khẩu bạn nhập không khớp");
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Vui lòng nhập số điện thoại.");
    }

    @Test
    public void TC_06_Register_Invalid_Phone() {
        // Action
        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("mooctacthanh@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("111111");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("111111");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("111");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        // driver.findElement(By.cssSelector("input#form#frmLogin button.btn_pink_sm")).click();

        // Verify
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
