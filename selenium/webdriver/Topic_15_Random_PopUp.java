package webdriver;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

// test
public class Topic_15_Random_PopUp {
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
    public void TC_01_Random_Popup_vnkEdu() {
        // step 01 - truy cap trang
        driver.get("https://vnk.edu.vn/");

        sleepInSecond(30);

        By mktPopup = By.cssSelector("div.tve-leads-conversion-object");

        // step 02 - popup co xuat hien thi close di
        if (driver.findElement(mktPopup).isDisplayed()) {
            driver.findElement(By.cssSelector("div.tve_ea_thrive_leads_form_close")).click();
            sleepInSecond(5);
            System.out.print("Popup hien thi");
        }
    }

    @Test
    public void TC_02_Random_Popup_NOT_in_DOM_dehieu() {
        driver.get("https://dehieu.vn/");

        By mktPopup = By.cssSelector("div.modal-content.css-modal-bt");

        if (driver.findElements(mktPopup).size() > 0 && driver.findElements(mktPopup).get(0).isDisplayed()) {
            System.out.print("Popup hien thi");
            driver.findElement(By.cssSelector("button.close")).click();
            sleepInSecond(3);
        }

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