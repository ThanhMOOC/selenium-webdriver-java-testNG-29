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
public class Topic_16_Shadow_DOM {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        actions = new Actions(driver);

        javascriptExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_Shadow_DOM() {
        driver.get("https://automationfc.github.io/shadow-dom");

        WebElement shadowHost = driver.findElement(By.cssSelector("div#shadow_host"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        String text = shadowRoot.findElement(By.cssSelector("span.info")).getText();

        Assert.assertEquals(text, "some text");

        WebElement nestedShadowHost = shadowRoot.findElement(By.cssSelector("div#nested_shadow_host"));
        SearchContext nestedShadowContent = nestedShadowHost.getShadowRoot();
        String nestedText = nestedShadowContent.findElement(By.cssSelector("div")).getText();

        Assert.assertEquals(nestedText, "nested text");

        WebElement checkboxShadow = shadowRoot.findElement(By.cssSelector("input[type='checkbox']"));

        Assert.assertFalse(checkboxShadow.isSelected());

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