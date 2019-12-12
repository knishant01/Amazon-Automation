package com.amazon.automate.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.amazon.automate.script.AssignmentAmazon;

/**
 * @author KNISHANT
 *
 */
public class Utility {
	
	WebDriver driver = null;
	private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	private static final String WEBDRIVER_FIREFOX_DRIVER = "webdriver.firefox.driver";
	private static final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";
	private static final Logger LOGGER = Logger.getLogger(Utility.class);
	static List<String>  nameList = null;
	
	public static Properties readfilefromPathUtil() throws Exception {
		Properties prop = null;
		// TODO Auto-generated method stub
		try {
			InputStream input = AssignmentAmazon.class.getClassLoader().getResourceAsStream("config.properties");
             prop = new Properties();

            if (input == null) {
              throw new Exception("Sorry, unable to find config.properties in classpath");
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return prop;
		
	}
	
	public static WebDriver setup(String browser) throws Exception {
		// Check if parameter passed from TestNG is 'firefox'
		if (browser.equalsIgnoreCase("firefox")) {
			Properties props = Utility.readfilefromPathUtil();

			System.setProperty(WEBDRIVER_FIREFOX_DRIVER, props.getProperty("webdriver.firefox.driver"));

			// create firefox instance
			return new FirefoxDriver();
		}
		// Check if parameter passed as 'chrome'
		else if (browser.equalsIgnoreCase("chrome")) {
			// set path to chromedriver.exe
			Properties props = Utility.readfilefromPathUtil();

			System.setProperty(WEBDRIVER_CHROME_DRIVER, props.getProperty("webdriver.chrome.driver"));

			// create chrome instance
			return new ChromeDriver();
		}
		// Check if parameter passed as 'IE'
		else if (browser.equalsIgnoreCase("ie")) {
			// set path to IE.exe
			Properties props = Utility.readfilefromPathUtil();

			System.setProperty(WEBDRIVER_IE_DRIVER, props.getProperty("webdriver.ie.driver"));

			// create IE instance
			return new InternetExplorerDriver();
		} else {
			// If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}

	}
	
	public static void searchCategory(WebDriver driver) {
		
		
		 driver.findElement(By.xpath("//*[@class='nav-search-dropdown searchSelect']")).click();
	 	 Select dropdown = new Select(driver.findElement(By.xpath("//*[@class='nav-search-dropdown searchSelect']")));
	 	  	 List<WebElement> dd =	dropdown.getOptions() ;
	 	 LOGGER.info("==========================start All Button Search functionality=================================");
	 	 LOGGER.info("Printing  the categories based on Filter criteria(ALL Button)");
	 	 for (int j = 0; j < dd.size(); j++) {
	        LOGGER.info(dd.get(j).getText());
	    }
	 	LOGGER.info("===========================end===================================================================");
	}
    
    public static void usbHubTotalItems(WebDriver driver) {
    	driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']")).sendKeys("usb hub 3.0");
        driver.findElement(By.xpath("//input[@value='Go' and @type='submit' and @tabindex='10']")).click();
        List<WebElement> e  = driver.findElements(By.xpath("//span[contains(text(),'results for')]"));
        LOGGER.info("==========================start usb hub 3.0 search functionality=================================");
	 	LOGGER.info("Printing the total number of products listed based on Filter criteria(usb hub 3.0)");
        for(WebElement w:e)
        LOGGER.info(w.getText().substring(0,w.getText().length()-4));
        LOGGER.info("===========================end===================================================================");
    }
    
    public static void primeEnabled(WebDriver driver) {
    	driver.findElement(By.xpath("//li[@aria-label='Prime Eligible' and @class='a-spacing-micro']//span[@class='a-list-item']//a//div//label//i")).click();
		if (driver.findElement(By.xpath("//span[@class='aok-inline-block s-image-logo-view'][1]")).isDisplayed()) {
			System.out.println("Prime Enabeled");

		} else {
			System.out.println("Prime Not Enabeled");
			takeSnapShot(driver,"C:/Users/knishant/Downloads/Prime_Not_Enabled.png");

		}
    }
    
    public static void primeDisabled(WebDriver driver) {
    	
    	driver.findElement(By.xpath("//li[@aria-label='Prime Eligible' and @class='a-spacing-micro']//span[@class='a-list-item']//a//div//label//i")).click();
     	
     	if(driver.findElement(By.xpath("//span[@class='aok-inline-block s-image-logo-view'][1]")).isDisplayed())
    	{
    	System.out.println("Prime Disabled");
    	
    	}
    	else
    	{
    	System.out.println("Prime Enabled");
    	takeSnapShot(driver,"C:/Users/knishant/Downloads/Prime_Enabled.png");
    	}
    }
    
    public static void sortItems(WebDriver driver) {
      nameList = null;
      List<Integer> myList = new ArrayList<Integer>();
      nameList = new ArrayList<String>();
 	  driver.findElement(By.className("a-dropdown-prompt")).click();
 	  driver.findElement(By.xpath("//li[@aria-labelledby='s-result-sort-select_1']")).click();
 	  
 	

	 List<WebElement> productNames = driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));

     for(WebElement productName:productNames) {
    	 nameList.add(productName.getText());
     }
     

		 
     List<WebElement> productPrices = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
		
     for(WebElement productPrice:productPrices) {
    	 myList.add(Integer.parseInt(productPrice.getText()));
     }
 	  
     List tmp = new ArrayList(myList);
     Collections.sort(tmp);
     boolean sorted = tmp.equals(myList);
     if(!sorted) {
    	 Utility.takeSnapShot(driver,"C:/Users/knishant/Downloads/ItemNotSorted.png");
     }
 	 
     for(int i=0;i<10;i++) {
    	 LOGGER.info("Product Name ==>"+nameList.get(i)+"##########"+"Product Price ==>"+myList.get(i));
    	 
     }
 	  
    }
    
    public static void takeSnapShot(WebDriver webdriver,String fileWithPath){

        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        //System.out.println(SrcFile.getAbsolutePath());
        //Move image file to new destination
        File DestFile=new File(fileWithPath);
        //System.out.println(DestFile.getPath());

        //Copy file at destination
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        }catch (IOException e) {

            System.err.println("An IOException was caught :"+e.getMessage());
        }
    }

	public static void addToCart(WebDriver driver) {
		
		driver.findElement(By.xpath("//span[contains(text(),'"+nameList.get(0)+"')]")).click();
		//driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();
		//driver.close();
		
		driver.findElement(By.xpath("//span[contains(text(),'"+nameList.get(1)+"')]")).click();
		
		 String MainWindow=driver.getWindowHandle();		
 		
	        // To handle all new opened window.				
	     Set<String> s1=driver.getWindowHandles();		
	     Iterator<String> i1=s1.iterator();		
	        		
	        while(i1.hasNext())			
	        {		
	            String ChildWindow=i1.next();		
	            		
	            if(!MainWindow.equalsIgnoreCase(ChildWindow))			
	            {    		
	                 
	                    // Switching to Child window
	                    driver.switchTo().window(ChildWindow);	                                                                                                           
	                    driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();	
	                                 
				// Closing the Child Window.
	                        driver.close();		
	            }		
	        }
	        driver.switchTo().window(MainWindow);	
	        driver.findElement(By.xpath("//a[@id='nav-cart']")).click();	
	        String itemCount = driver.findElement(By.xpath("//span[@class='nav-cart-count nav-cart-1']")).getText();
	        if(itemCount.equalsIgnoreCase(itemCount)) {
	        	takeSnapShot(driver,"C:/Users/knishant/Downloads/Item_added_Successfully.png");
	        }
	        else
	        {
	        	takeSnapShot(driver,"C:/Users/knishant/Downloads/Item_added_Failed.png");
	        }
	        
		
	}
}
