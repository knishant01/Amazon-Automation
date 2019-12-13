package com.amazon.automate.script;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.automate.util.Utility;

/**
 * Unit test for AssigmentAmazon Automation.
 * Author by Nishant Kumar
 */
public class AssignmentAmazon 
  
{   

	private static final Logger LOGGER = Logger.getLogger(AssignmentAmazon.class);
	private WebDriver driver = null;
	private WebDriverWait wait = null;
	
	public static void main(String... s) throws Exception
	{
	    //fetching browser name defined in config.properties file
		
		Properties props = Utility.readfilefromPathUtil();

		/*
		 * setup method implemented for dynamic loading of driver class based on
		 * corresponding browser name as mentioned in config properties
		 */
		WebDriver driver = Utility.setup(props.getProperty("browser.name"));
		
	    driver.get(props.getProperty("amazon.url"));
	    
 	    driver.manage().window().maximize();
 	    
 	  
 	    LOGGER.info("Amazon website opened successully !!!");
 	    
 	    /*
 	     * On the home page next to search box there is ‘All’ button, 
 	     * click on that and print the categories available in the log 
 	     * 
 	     */
 	    
 	      Utility.searchCategory(driver);
 	    
 	    /*
 	     * Search for “usb hub 3.0”and print the total number of products listed for that search
 	     */
 	     
 	     Utility.usbHubTotalItems(driver);
 	     
 	    
		/*
		 * Add filter for ‘Prime’ and check if only ‘Prime’ enabled items are listed
		 */

 	     Utility.primeEnabled(driver);
		
		/*
		 * Disable ‘Prime’ and see if list is changed
		 */
		
 	     Utility.primeDisabled(driver);
 	     /*
 	      * 
 	      * Sort by ‘Low to High’ and see if the list of items are sorted accordingly in that page. i.e check if the. 
 	      * [print :first 5-10 products name with price in ascending order, as they appear]
 	      */
 	     
 	     Utility.sortItems(driver);
 	     
 	     /*
 	      * Select any two items and add to cart. 
 	      * Verify the count in cart is reflecting properly and check if the two items that were selected is present in the cart
 	      */
 	     Utility.addToCart(driver);
 	     
 	     
 	
 	
 	 
	
	
	}

	
	

}
	 	 
	
