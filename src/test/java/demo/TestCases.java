package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestCases {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Setup WebDriverManager for Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void fullScrren() {
        driver.manage().window().maximize();
    }

    @Test
    public void testOpenBrowser() throws InterruptedException {
        // Open a website
        driver.get("https://www.flipkart.com/");
    }

    @Test
    public void searchItem() throws InterruptedException {
        WebElement searchItem = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        searchItem.sendKeys("Washing Machine");
        Thread.sleep(500);
        searchItem.sendKeys(Keys.ARROW_DOWN);
        // Press Enter to select the highlighted option
        searchItem.sendKeys(Keys.ENTER);

    }

    @Test
    public void selectItem() throws InterruptedException {
        WebElement selectPopularity = driver.findElement(By.xpath("//div[@class='sHCOk2']/div[2]"));
        selectPopularity.click();
        Thread.sleep(500);
    }

    @Test
    public void listWashingMachine() throws InterruptedException {
        List<WebElement> ratingElements = driver.findElements(By.xpath("//span[contains(@id, 'productRating_')]/div"));
        for (WebElement ratingElement : ratingElements) {
            double rating = Double.parseDouble(ratingElement.getText());
            if (rating <= 4.0) {
                System.out.println(ratingElement.getText());
            }
        }
    }

    @Test
    public void searchIphone() throws InterruptedException {
        WebElement searchInput = driver.findElement(By.cssSelector("div.MoPwtI > input.zDPmFV"));
        // Clicking Ctrl + A to select all existing text
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));

        // Pressing the delete key to clear the selected text
        searchInput.sendKeys(Keys.DELETE);

        // Provide new text
        searchInput.sendKeys("iphone");
        searchInput.sendKeys(Keys.ENTER);
    }

    @Test
    public void listIphone() throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> products = driver.findElements(By.cssSelector("div[data-id]"));

        for (WebElement product : products) {
            try {
                WebElement discountElement = product.findElement(By.cssSelector("div.UkUFwK > span"));
                String discountText = discountElement.getText();

                String discountPercent = discountText.replaceAll("[^0-9]", "");

                if (!discountPercent.isEmpty() && Integer.parseInt(discountPercent) > 17) {
                    WebElement titleElement = product.findElement(By.cssSelector("div.KzDlHZ"));
                    String title = titleElement.getText();
                    System.out.println("Title: " + title);
                    System.out.println("Discount %: " + discountPercent);
                    System.out.println("-------------------------");
                }
            } catch (StaleElementReferenceException e) {
                // Retry operation
                System.out.println("StaleElementReferenceException occurred. Retrying operation.");
                // You can add a retry mechanism here if needed
            }
        }
    }
}
