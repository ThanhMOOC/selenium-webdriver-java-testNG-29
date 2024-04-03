package webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

// test
public class Topic_15_Fixed_PopUp_in_DOM {
    WebDriver driver;

    Actions actions;
    JavascriptExecutor javascriptExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();

        /*
        selenium 3x
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        actions = new Actions(driver);

        javascriptExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_Popup_in_DOM_NgoaiNgu() {
        // step 01 - truy cap trang
        driver.get("https://ngoaingu24h.vn/");

        // step 02 - click Dang nhap button
        driver.findElement(By.cssSelector("button.login_")).click();

        sleepInSecond(3);

        // step 03 - kiem tra popup hien thi
        Assert.assertTrue(driver.findElement(By.cssSelector("div.modal.fade.in")).isDisplayed());

        // step 04 - nhaajp thong tin
        driver.findElement(By.cssSelector("div.modal.fade.in input#account-input")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("div.modal.fade.in input#password-input")).sendKeys("automationfc");

        // step 05 - click Dang nhap button vaf kiem tra
        driver.findElement(By.cssSelector("div.modal.fade.in button.btn-v1.btn-login-v1")).click();
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.cssSelector("div.modal.fade.in div.row.error-login-panel")).getText(), "Tài khoản không tồn tại!");

        // step 06 - dong popup
        driver.findElement(By.cssSelector("div.modal.fade.in button.close")).click();
        // step 07 - kiem tra popup ko hien thi
        Assert.assertFalse(driver.findElement(By.cssSelector("div.modal.fade")).isDisplayed());
    }

    @Test
    public void TC_02_Popup_in_DOM_Kyna() {
        // wweb ngung hoat dong ko test dc

        // step 01 - truy cap trang
        driver.get("https://skills.kynaenglish.vn/");

        // step 02 - click Dang nhap button
        driver.findElement(By.cssSelector("a.login-btn")).click();

        // step 03 - kiem tra popup hien thi
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='k-popup-account-mb-content']")).isDisplayed());

        // step 04 - nhap thong tin
        driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#password-login")).sendKeys("automation@gmail.com");

        // step 05 - click Dang nhap button
        driver.findElement(By.cssSelector("button#btn-submit-login")).click();

        //step 06 - kiem tra message
        Assert.assertEquals(driver.findElement(By.cssSelector("button#btn-submit-login")).getText(), "Sai tên đăng nhập hoặc mật khẩu");

        // step 07 - tat popup
        driver.findElement(By.xpath("//div[@class='k-popup-account-mb-content']//button[@type='button']")).click();

        // step 08 - kiem tra popup ko display
        Assert.assertFalse(driver.findElement(By.xpath("//div[@class='k-popup-account-mb-content']")).isDisplayed());
    }

    @Test
    public void TC_03_Popup_NOT_in_DOM() {

        // step 01 - truy caapj trang
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//button[text()='Decline optional cookies']")).click();

        sleepInSecond(3);

        // step 02 - click create new account
        driver.findElement(By.xpath("//a[text()='Create new account']")).click();

        sleepInSecond(3);

        // step 03 - kiem tra popup hien thi
        Assert.assertTrue(driver.findElement(By.cssSelector("div._8ien")).isDisplayed());

        // step 04 - tat popup
        driver.findElement(By.cssSelector("img._8idr.img")).click();


        // truong hop popup NOT in DOM thi khong dung findElement duoc vi se Failed
        // cach xu li se la dung findElements (so nhieu) va kiem tra size cua list tra ve bang 0
        // findElement se tra ve 1 list cac elements => neu ko co element thi list size = 0
        // trong truong hop nay do van bi phu thuoc vao phuong thuc findElement() nen van dung impliciteWait duoc set trong beforeClass
        // => can phai set lai wait va giam thoi gian doi xuong de rut ngan thoi gian chay test case

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // step 05 - kiem tra popup ko hien thi
        Assert.assertEquals(driver.findElements(By.cssSelector("div._8ien")).size(), 0);
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