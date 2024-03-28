package webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// test
public class Topic_14_Actions {
    WebDriver driver;
    String osName = System.getProperty("os.name");

    Actions actions;

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
    }

    @Test
    public void TC_01_Hover_Element() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/jquery-tooltip/");

        // step 02 - hover chuột vào textbox
        actions.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();

        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_02_Hover_Element() {
        // step 01 - truy cập trang
        driver.get("http://www.myntra.com/");

        // step 02 - hover vafo KIDS
        actions.moveToElement(driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Kids']"))).perform();
        sleepInSecond(3);

        // step 03 - Click vào Home & Bath
        driver.findElement(By.xpath("//a[text()='Home & Bath']")).click();
        sleepInSecond(3);

        // step 04 - Kiểm tra đã navigate
        Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(), "Kids Home Bath");
        Assert.assertEquals(driver.findElement(By.cssSelector("h1.title-title")).getText(), "Kids Home Bath");
    }

    @Test
    public void TC_03_Hover_Element() {
        // step 01 - truy cập trang
        driver.get("https://www.fahasa.com/");

        // step 02 - hover icon Menu
        actions.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
        sleepInSecond(3);

        // step 03 - Hover lên "Sách trong nước" và kiểm tra
        actions.moveToElement(driver.findElement(By.xpath("//a[@title='Sách Trong Nước']"))).perform();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//span[@class='menu-title']")).getText(), "Sách Trong Nước");
    }

    @Test
    public void TC_04_Click_Hover_SelectMultiElement() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List <WebElement> allItems = driver.findElements(By.cssSelector("li.ui-state-default"));
        Assert.assertEquals(allItems.size(), 20);

        // step 02 - click và hold từ 1 tới 4
        actions.clickAndHold(allItems.get(0))   // click và giữ 1
                .pause(2000)                    // dừng 2s
                .moveToElement(allItems.get(3)) // di chuyển tới 4
                .pause(2000)                    // dừng 2s
                .release()                      // thả chuột ra
                .perform();                     // hoàn thành actions

        List <String> allExpectedText = new ArrayList<String>();
        allExpectedText.add("1");
        allExpectedText.add("2");
        allExpectedText.add("3");
        allExpectedText.add("4");

        List <WebElement> selectedItems = driver.findElements(By.cssSelector("li.ui-selected"));

        List <String> actualItemText = new ArrayList<String>();

        for(WebElement e : selectedItems){
            actualItemText.add(e.getText());
        }

        // step 03 - kiểm tra 4 phần tử đã chọn
        Assert.assertEquals(actualItemText, allExpectedText);
    }

    @Test
    public void TC_05_Click_and_SelectRandomElement() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List <String> expectedElementText = new ArrayList<String>();
        expectedElementText.add("1");
        expectedElementText.add("3");
        expectedElementText.add("6");
        expectedElementText.add("11");

        actions.keyDown(Keys.CONTROL)
                .moveToElement(driver.findElement(By.xpath("//li[text()='1']"))).click()
                .moveToElement(driver.findElement(By.xpath("//li[text()='3']"))).click()
                .moveToElement(driver.findElement(By.xpath("//li[text()='6']"))).click()
                .moveToElement(driver.findElement(By.xpath("//li[text()='11']"))).click()
                .release()
                .perform();

        List <WebElement> selectedElements = driver.findElements(By.cssSelector("li.ui-selected"));
        List <String> selectedElementsText = new ArrayList<String>();

        for(WebElement e : selectedElements) {
            selectedElementsText.add(e.getText());
        }

        Assert.assertEquals(selectedElementsText, expectedElementText);

    }

    @Test
    public void TC_06_Double_Click() {
        // step 01 - truy cập trang
        driver.get("https://automationfc.github.io/basic-form/index.html");
        sleepInSecond(3);

        WebElement doubleClickBtn = driver.findElement(By.xpath("//button[text()='Double click me']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", doubleClickBtn);

        sleepInSecond(3);

        // step 02 - double click vào button : Double click me
        actions.doubleClick(doubleClickBtn).perform();

        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
    }

    @Test
    public void TC_07_Right_Click() {
        // step 01 - truy cập trang
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        // step 02 - right click element
        actions.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();

        // step 03 - kiểm tra Quit hiển thị
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());

        // step 04 - hover chuột vào Quit
        actions.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();

        // step 05 - verify element visible + hover
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-visible.context-menu-hover")).isDisplayed());

        // step 04 - click quit
        actions.click(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();

        // step 05 - accept alert
        driver.switchTo().alert().accept();

        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());
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