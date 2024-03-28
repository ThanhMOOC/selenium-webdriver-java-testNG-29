package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;

// test
public class Topic_12_Default_Checkbox_Radiobutton {
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
    public void TC_02_Default_Checkbox_Radiobutton() {
        // stepp 01 - truy cập trang
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        sleepInSecond(3);

        WebElement dualZoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/parent::li/input"));
        // scroll to Dual zone checkbox
        WebElement e0 = driver.findElement(By.cssSelector("h1.kd-title"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e0);

        // step 02 - chọn Dual-zone air conditioning
        dualZoneCheckbox.click();

        // step 03 - kiểm tra đã chọn
        Assert.assertTrue(dualZoneCheckbox.isSelected());

        // step 04 - bỏ chọn và kiểm tra
        dualZoneCheckbox.click();
        Assert.assertFalse(dualZoneCheckbox.isSelected());

        // step 05 - truy cập trang
        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        sleepInSecond(3);

        WebElement e1 = driver.findElement(By.cssSelector("h1.kd-title"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e1);
        // step 06 - chọn 2.0 Petrol
        WebElement petrol2Radio = driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/parent::li/input"));
        petrol2Radio.click();
        Assert.assertTrue(petrol2Radio.isEnabled());

        // step 07 - kiểm tra đã chọn nếu chưa chọn lại
        if(!petrol2Radio.isSelected()) {
            petrol2Radio.click();
            Assert.assertTrue(petrol2Radio.isEnabled());
        }
    }
    @Test
    public void TC_03_Default_Checkbox_Radiobutton() {
        // step 01 - truy cập trang
        driver.get("https://material.angular.io/components/radio/examples");

        sleepInSecond(3);

        WebElement summerRadio = driver.findElement(By.xpath("//label[text()='Summer']/preceding-sibling::div/input"));

        // step 02 - chọn Summer
        summerRadio.click();
        sleepInSecond(3);

        // step 03 - kiểm tra đã chọn nếu chưa chọn lại
        if(!summerRadio.isSelected()) {
            summerRadio.click();
            Assert.assertTrue(summerRadio.isEnabled());
        }

        // step 04 - truy cập trang khác
        driver.get("https://material.angular.io/components/checkbox/examples");

        sleepInSecond(3);

        WebElement checkedCheckbox, indeterminateCheckbox;
        checkedCheckbox = driver.findElement(By.xpath("//label[text()='Checked']/preceding-sibling::div/input"));
        indeterminateCheckbox = driver.findElement(By.xpath("//label[text()='Indeterminate']/preceding-sibling::div/input"));

        // step 05 - chọn Checked và Indeterminate
        checkedCheckbox.click();;
        indeterminateCheckbox.click();

        sleepInSecond(3);

        // step 06 - kiểm tra đã chọn
        Assert.assertTrue(checkedCheckbox.isSelected());
        Assert.assertTrue(indeterminateCheckbox.isSelected());

        // step 07 - bỏ chọn
        checkedCheckbox.click();;
        indeterminateCheckbox.click();

        sleepInSecond(3);

        // step 08 - kiểm tra đã bỏ chọn
        Assert.assertFalse(checkedCheckbox.isSelected());
        Assert.assertFalse(indeterminateCheckbox.isSelected());
    }

    @Test
    public void TC_04_Select_all_checkbox() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/multiple-fields/");

        // step 02 - chọn all checkboxes
        List <WebElement> allCheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));

        for (WebElement e : allCheckboxes) {
            e.click();
            Assert.assertTrue(e.isSelected());
        }

        for (WebElement e : allCheckboxes) {
            e.click();
            Assert.assertFalse(e.isSelected());
        }

        // chọn Heart Attack
        driver.findElement(By.cssSelector("input[value='Heart Attack']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("input[value='Heart Attack']")).isSelected());
    }

//    @Test
    public void TC_05_Custom_Checkbox_Radiobutton() {
        // step 01 - truy cập trang
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

    }
    @Test
    public void TC_06_Custom_Checkbox_Radiobutton() {
        // step 01 - truy cập trang
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

        // step 02 - click Cần Thơ
        driver.findElement(By.xpath("//div[@aria-label='Cần Thơ']")).click();

        // step 03 - kiểm tra đã chọn Cần Thơ
        Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
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