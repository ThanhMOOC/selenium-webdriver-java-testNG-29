package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static java.time.Duration.ofSeconds;

// test
public class Topic_10_Custom_Dropdown {
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
    public void TC_01_JQuery() {
        // step 01 - truy câp trang
        driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

        sleepInSecond(5);

        // Step 02 - chọn Medium
        selectItemDropdownByCss("span#speed-button", "ul#speed-menu div", "Medium");

        // step 03 - kiểm tra
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(), "Medium");

        // step 04
        selectItemDropdownByCss("span#speed-button", "ul#speed-menu div", "Slower");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(), "Slower");

        selectItemDropdownByCss("span#speed-button", "ul#speed-menu div", "Faster");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(), "Faster");
    }

    @Test
    public void TC_02_ReactJS() {
        // step 01 - truy câp trang
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

        // Step 02 - chọn Jenny Hess
        selectItemDropdownByCss("div.divider", "div.menu span", "Jenny Hess");

        // step 03 - kiểm tra
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Jenny Hess");


        selectItemDropdownByCss("div.divider", "div.menu span", "Justen Kitsune");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Justen Kitsune");

        selectItemDropdownByCss("div.divider", "div.menu span", "Christian");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Christian");

        selectItemDropdownByCss("div.divider", "div.menu span", "Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider")).getText(), "Stevie Feliciano");
}

    @Test
    public void TC_03_VueJS() {
        // step 01 - truy câp trang
        driver.get("https://mikerodham.github.io/vue-dropdowns/");

        selectItemDropdownByCss("li.dropdown-toggle", "ul.dropdown-menu a", "First Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");

        selectItemDropdownByCss("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");

        selectItemDropdownByCss("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Third Option");
    }

    @Test
    public void TC_04_Editable() {
        // step 01 - truy câp trang
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        selectItemEditableDropdownByCss("input.search", "div.item span", "Algeria");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Algeria");

        selectItemEditableDropdownByCss("input.search", "div.item span", "Albania");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Albania");

        selectItemEditableDropdownByCss("input.search", "div.item span", "Belarus");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Belarus");

        selectItemEditableDropdownByCss("input.search", "div.item span", "Benin");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Benin");

    }

    public void selectItemDropdownByCss(String dropdownLocator, String allItems, String targetItem) {
        driver.findElement(By.cssSelector(dropdownLocator)).click();

        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItems)));

        List <WebElement> items = driver.findElements(By.cssSelector(allItems));

        for (WebElement e : items) {
            if (e.getText().equals(targetItem)){
                e.click();
                break;
            }
        }
    }

    public void selectItemEditableDropdownByCss(String dropdownLocator, String allItems, String targetItem) {
        driver.findElement(By.cssSelector(dropdownLocator)).clear();
        driver.findElement(By.cssSelector(dropdownLocator)).sendKeys(targetItem);

        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItems)));

        List <WebElement> items = driver.findElements(By.cssSelector(allItems));

        for (WebElement e : items) {
            if (e.getText().equals(targetItem)){
                e.click();
                break;
            }
        }
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
        driver.quit();
    }
}