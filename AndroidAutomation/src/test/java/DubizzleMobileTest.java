

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DubizzleMobileTest {
    public static AndroidDriver driver;
    @BeforeTest

    public void appiumSetup() throws Exception {
        /*write code to start appium server*/


        //Set the Desired Capabilities
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8.0.0");
        caps.setCapability("automationName","UiAutomator2");
        caps.setCapability("udid", "988a5c393054574f32"); //Give Device ID of your mobile phone
        caps.setCapability("deviceName", "Galaxy S8+");
        caps.setCapability("appPackage", "com.dubizzle.horizontal");
        caps.setCapability("appActivity", "com.dubizzle.horizontal.activities.MainActivity");
        caps.setCapability("noReset", "true");
        caps.setCapability("browserName", "true");

        /*Instantiate Appium Driver and Launch Dubizzle App*/
        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            Thread.sleep(5000);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void dubizzleMobileTest() throws Exception{
        try {
            /*Step 1 - Navigate to  "Property" */
            driver.findElement(By.xpath("//android.widget.TextView[@text='Property']")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 2 Navigate to  "Property For Rent" */
            driver.findElement(By.xpath("//android.widget.TextView[@text='Property for Rent']")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(5000);

            /*Step 3 Navigate to Residential Unit for Rent*/
            driver.findElement(By.xpath("//android.widget.TextView[@text='Residential Units for Rent'][@index=0]")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 4 Navigate to Apartment/Flat for Rent”*/
            driver.findElement(By.xpath("//android.widget.TextView[@text='Apartment/Flat for Rent']")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Thread.sleep(8000);

            /* Filter Results by Location “Dubai Marina” */
            /* Step 5 - Click on Filter option*/
            driver.findElement(By.xpath("//android.widget.TextView[@text='FILTER']")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 6 - Click on Edit Text*/
            driver.findElement(By.xpath("//android.widget.TextView[@text='e.g. Dubai Marina'][@index=1]")).click();
            //driver.findElement(By.xpath("//android.widget.TextView[@text='e.g. Dubai Marina']")).sendKeys("Dubai Marina");
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 7 -  Click on Edit Text*/
            driver.findElement(By.xpath("//android.widget.EditText[@text='e.g. Dubai Marina']")).click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 8 - Enter Text Dubai Marina for search */
            driver.findElement(By.xpath("//android.widget.EditText[@text='e.g. Dubai Marina']")).sendKeys("Dubai Marina");
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 9*/
            driver.findElement(By.xpath("//android.widget.TextView[@text='Dubai Marina'][@index=0]")).click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 10 -  Click on DONE button */
            driver.findElement(By.id("com.dubizzle.horizontal:id/btn_done")).click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            Thread.sleep(3000);

            /*Step 11 - Click on Search button - id*/
            driver.findElement(By.id("com.dubizzle.horizontal:id/btnShowResults")).click();
            Thread.sleep(8000);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

            /*Step 12 - get the list of search elements */
            Boolean bFlag = true;
            int counter = 0;
            while(counter<15){
                List<MobileElement> elementsAddress = driver.findElements(By.id("com.dubizzle.horizontal:id/tv_location"));
                List<MobileElement> elementsPrice = driver.findElements(By.id("com.dubizzle.horizontal:id/tv_price"));

                counter += elementsAddress.size();
                for(MobileElement ele: elementsAddress){
                    System.out.println(ele.getText());
                    if(!ele.getText().contains("Dubai Marina")) {
                        bFlag = false;
                    }
                }
                /*This for loop is just to print the price associated with each property*/
                for(MobileElement ele: elementsPrice){
                    System.out.println(ele.getText());
                }
                try{
                    driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.dubizzle.horizontal:id/recycler\")).scrollForward()");

                }catch(Exception e){

                }
                Assert.assertTrue(bFlag,"Dubai Marina text not found in first 15 search results");
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterTest
    public void tearDown()
    /*Close the driver*/{
        driver.quit();
    }
}
