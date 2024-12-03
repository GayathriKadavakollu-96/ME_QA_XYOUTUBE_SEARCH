package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalDate;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        @BeforeMethod
        public void goToYoutube() throws InterruptedException{
                Wrappers.goToURLAndWait(driver, "https://www.youtube.com/");
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);
        }

        @Test (enabled = true)
        public void testCase01(){
                System.out.println("Running testCase01");
                Assertion hardAssertion=new Assertion();
                String currentURL=driver.getCurrentUrl();
                hardAssertion.assertEquals("https://www.youtube.com/",currentURL);
                WebElement about=driver.findElement(By.linkText("About"));
                Wrappers.findElementAndClickWE(driver, about);
                String text=driver.findElement(By.xpath("//section[@class='ytabout__content']")).getText();
                System.out.println(text);
        }

        @Test (enabled = true)
        public void testCase02() throws InterruptedException{
                System.out.println("Running testCase02");
                WebElement movies=driver.findElement(By.partialLinkText("Movies"));
                Wrappers.findElementAndClick(driver, movies);
                WebElement rightArrow=driver.findElement(By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[contains(@class, 'grid-subheader')]//following-sibling::div[@id='contents']//button[@aria-label='Next']"));
                Wrappers.clickTillUnclickable(driver, rightArrow);
                String mature=driver.findElement(By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[contains(@class, 'grid-subheader')]//following-sibling::div[@id='contents']//span[@title='Kung Fu Panda 4']/../..//following-sibling::ytd-badge-supported-renderer//div//following-sibling::div[@aria-label='U']")).getText();
                SoftAssert softAssert=new SoftAssert();
                softAssert.assertEquals ("A", mature, "Movie is not marked A and it's not Mature");
                WebElement mveCategory=driver.findElement(By.xpath("//span[contains(text(),'Top selling')]//ancestor::div[contains(@class, 'grid-subheader')]//following-sibling::div[@id='contents']//span[contains(text(), 'Animation')]"));
                softAssert.assertTrue(mveCategory.isDisplayed(), "Movie category is not existed");
                
        }

        @Test(enabled = true)
        public void testCase03() throws InterruptedException{
                System.out.println("Running testCase03");
                WebElement music=driver.findElement(By.partialLinkText("Music"));
                Wrappers.findElementAndClick(driver, music);
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);
                WebElement rightArrow=driver.findElement(By.xpath("//span[contains(text(),'Biggest Hits')]//ancestor::div[contains(@class, 'grid-subheader')]//following-sibling::div[@id='contents']//button[@aria-label='Next']"));
                Wrappers.clickTillUnclickable(driver, rightArrow);
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);
                WebElement title=driver.findElement(By.xpath("//span[contains(text(),'Biggest Hits')]//ancestor::div[6]//div[@id='contents']//span[contains(text(), 'Bollywood Dance Hitlist')]"));
                System.out.println("Title of the playlist is: "+title.getText());
                By locator_TrackCount=By.xpath("//span[contains(text(),'Biggest Hits')]//ancestor::div[6]//div[@id='contents']//span[contains(text(), 'Bollywood Dance Hitlist')]//ancestor::div[contains(@class, 'yt-lockup-view')]//preceding-sibling::a//div[contains(@class, 'badge-shape-wiz__text')]");
                String result=Wrappers.findElementAndPrint(driver, locator_TrackCount, driver.findElements(locator_TrackCount).size()-1);
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);
                SoftAssert sa=new SoftAssert();
                sa.assertTrue(Wrappers.convertToNumericValue(result.split(" ")[0])<=50, "The list has more than 50 songs");
        }

        @Test(enabled = true)
        public void testCase04() throws InterruptedException{
                System.out.println("Running testCase04");
                WebElement news=driver.findElement(By.partialLinkText("News"));
                Wrappers.findElementAndClick(driver, news);
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);

                WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
                //Wait for the element to be clickable
                WebElement contentCards=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Latest news')]//ancestor::div[contains(@id, 'header-container')]//following-sibling::div[@id='contents']//div[@id='dismissible']")));
                Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);
                long sumOfVotes=0;
                for(int i=0;i<3;i++){
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='header']"), contentCards, i));
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='body']"), contentCards, i));
                        try{
                                String res=Wrappers.findElementAndPrintWE(driver, By.xpath("//span[@id='vote-count-middle']"), contentCards, i);
                                sumOfVotes=sumOfVotes+Wrappers.convertToNumericValue(res);
                        }catch(NoSuchElementException e){
                                System.out.println("Vote not present: "+e.getMessage());
                        }
                        System.out.println(sumOfVotes);
                        Thread.sleep((new java.util.Random().nextInt(3)+2)*1000);

                }
        }

        /*[0]
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}