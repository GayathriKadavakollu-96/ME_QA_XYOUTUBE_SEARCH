package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void goToURLAndWait(WebDriver driver, String url){
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(WebDriver-> ((JavascriptExecutor)WebDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void clickTillUnclickable(WebDriver driver, WebElement locator){
        try{
            while(locator.isDisplayed()){
                    locator.click();
            }  
        }catch(Exception e){
            System.out.println("Exception occured!: ");
        }
        
        // Integer iter=0;
        // while(iter<maxIterations){
        //     try{
        //         findElementAndClick(driver,locator);
        //     }catch(Exception e){
        //         System.out.println("Exception occured!: ");
        //         break;
        //     }
        // }
    }
    
    public static void findElementAndClick(WebDriver driver, WebElement locator) throws InterruptedException{
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.visibilityOf(locator));
        locator.click();
        Thread.sleep(1000);
    }

    public static void findElementAndClickWE(WebDriver driver, WebElement element){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        element.click();
    }

    public static String findElementAndPrint(WebDriver driver, By locator, int elementNo){
        WebElement we=driver.findElements(locator).get(elementNo);

        String txt=we.getText();
        return txt;
    }

    public static String findElementAndPrintWE(WebDriver driver, By locator, WebElement we, int elementNo){
        WebElement element=we.findElements(locator).get(elementNo);

        String txt=element.getText();
        return txt;
    } 
    
    public static long convertToNumericValue(String value){
        //Trim the string to remove any leading or trailing spaces
        value=value.trim().toUpperCase();
        //Check if the last character is Non-numeric and determine the multiplier
        char lastChar=value.charAt(value.length()-1);
        int multiplier=1;
        switch (lastChar) {
            case 'K':
                multiplier=1000;
                break;
            case 'M':
                multiplier=1000000;
                break;
            case 'B':
                multiplier=1000000000;
                break;        
            default:
                //If the last character is numeric, parse the entire string
                if(Character.isDigit(lastChar)){
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid format: "+value);
        }
        //Extract the numeric value before the last character
        String numericPart=value.substring(0, value.length()-1);
        double number=Double.parseDouble(numericPart);

        //Calculate the final value
        return (long) (number*multiplier);

    }
}
