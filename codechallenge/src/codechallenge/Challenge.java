package codechallenge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Challenge {
	

    // To Launch Chrome browser and open newegg app & login to the application.
	public static void main(String[] args) {
            System.setProperty("webdriver.chrome.driver","C:\\Users\\krishk\\Downloads\\chromedriver_win32\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            JavascriptExecutor jse = (JavascriptExecutor) driver;
      
	        String baseUrl = "https://www.newegg.com/";
	        driver.manage().window().maximize();
	        driver.get(baseUrl);
	        driver.findElement(By.xpath("//*[@id=\"usaSite\"]/a")).click();
	        driver.findElement(By.id("UserName")).sendKeys("ctschallenge20@gmail.com");
	        driver.findElement(By.id("UserPwd")).sendKeys("Challenge20");
	        driver.findElement(By.className("btnSubmit")).click();
        
        
        // Fetching data from Excel file one by one
        try {
            
        	  FileInputStream file = new FileInputStream(new File("C:\\Users\\krishk\\Downloads\\List.xlsx")); 
        	  XSSFWorkbook workbook = new XSSFWorkbook(file);
        	 
        	  XSSFSheet sheet = workbook.getSheetAt(0);
       
        	  for (int i=1; i <= sheet.getLastRowNum(); i++){
        		  
        		  WebElement searchbox = driver.findElement(By.id("haQuickSearchBox"));       	 
       	          String keyword = sheet.getRow(i).getCell(0).getStringCellValue();
        	      String keyword1 = sheet.getRow(i).getCell(1).getStringCellValue();
       	          float data= (float)sheet.getRow(i).getCell(2).getNumericCellValue();
        	      searchbox.clear();
        	      searchbox.sendKeys(keyword + "," + keyword1);
        	      searchbox.submit();
        	      driver.navigate().refresh();
        	        
        	   // Filter by Price details using Excel data
        	        Boolean isPresent = driver.findElements(By.id("txtLeftNavPriceMin")).size() > 0;
        	        if(isPresent)
        	        {
        	        	driver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);
        	        	 jse.executeScript("window.scrollBy(0,750)");
        	        	 driver.findElement(By.cssSelector("#facet4015 > dd > div.filter-range > label > span")).click();
        	        	 driver.findElement(By.id("txtLeftNavPriceMin")).sendKeys(String.valueOf(data));
              	         driver.findElement(By.id("txtLeftNavPriceMax")).sendKeys(String.valueOf(data));
              	         driver.findElement(By.id("fab_4015")).click();
          	             driver.navigate().refresh();
          	             driver.findElement(By.className("item-title")).click();
          	             driver.findElement(By.cssSelector("#landingpage-cart > div > div.nav-col.call-to-action.call-to-action-main-product > button")).click();
          	    
        	        }
        	     else
        	        {
        	        	driver.findElement(By.cssSelector("#bodyArea > section > div > div > div > div > div > div > div.row-body > div.row-body-inner > div.list-wrap > div.items-view.is-grid > div > div > div.item-action > div > div.item-button-area > button")).click();
        	        }
      	       
    	 
        	}
        	  //To close the workbook
        	  workbook.close();
        	  file.close();
	            
        	  // Validating the cart with Added items and Total Price details
        	  String TotalResult = driver.findElement(By.cssSelector("#bodyArea > div.article > form:nth-child(1) > table.head > thead > tr > th:nth-child(1) > h1")).getText();
        	  System.out.println("Printing " + TotalResult);
        	  
        	  // Removing Thermostat from Cart and Updating the cart with Updated items and Total Price details
        	  driver.findElement(By.id("9SIAB0M44F3022.1.0.0")).click();
 	          driver.findElement(By.id("removeFromCart")).click();
 	          String UpdatedResult = driver.findElement(By.cssSelector("#bodyArea > div.article > form:nth-child(1) > table.head > thead > tr > th:nth-child(1) > h1")).getText();
       	      System.out.println("Printing " + UpdatedResult);
       	      
       	      // Adding 4 qty in Cart for Micro SD Card and Updating the cart with Updated items and Total Price details
       	      driver.findElement(By.cssSelector("#ITEM\\2e 9SIA12K6PZ9060\\2e 1\\2e 0\\2e 0")).sendKeys(Keys.chord(Keys.CONTROL, "a"),"4");;
       	      driver.findElement(By.cssSelector("#bodyArea > div.article > form:nth-child(1) > table.head > tbody > tr.tool-bar > td:nth-child(2) > a")).click();
       	      String AddedResult = driver.findElement(By.cssSelector("#bodyArea > div.article > form:nth-child(1) > table.head > thead > tr > th:nth-child(1) > h1")).getText();
     	      System.out.println("Printing " + AddedResult);
     	      
     	      // Clicking on Secure Checkout Option and Validating the Error Message for Mandatory field like First Name, Last Name, Address, Zip Code and Phone number
     	      driver.findElement(By.cssSelector("#bodyArea > div.article > div.step-navigation > div.actions.l-right > div > a.button.button-primary.has-icon-right")).click();
     	      driver.findElement(By.cssSelector("#orderSummaryPanelAndPayment > div > div.additional-info-groupbox > div > div > a")).click();
     	      System.out.println("Please Type Required Infromation");
     	      driver.findElement(By.cssSelector("#bodyArea > div.page-title.page-title-checkout > ul > li:nth-child(1) > span > a")).click();
     	      driver.findElement(By.xpath("//*[@id=\"usaSite\"]/a/i")).click();     	     
     	      driver.findElement(By.linkText("Logout")).click();
     	      System.out.println("Logged Out Successfully");
       	      
     	      
     	      // Reset Password Functionality
     	     driver.get(baseUrl);
             driver.findElement(By.xpath("//*[@id=\"usaSite\"]/a")).click();
             driver.findElement(By.linkText("Forgot your password?")).click();
             driver.findElement(By.cssSelector("#QuickLinks > dd > form > table > tbody > tr:nth-child(3) > td:nth-child(2) > input[type=\"text\"]")).sendKeys("ctschallenge20@gmail.com");
             driver.findElement(By.className("btnSubmit")).submit();
             driver.navigate().to("https://www.google.com/gmail/about/#");
             driver.findElement(By.linkText("SIGN IN")).click();
             driver.findElement(By.id("identifierId")).sendKeys("ctschallenge20@gmail.com");
             driver.findElement(By.cssSelector("#identifierNext > content > span")).click();
             driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
             driver.findElement(By.cssSelector("#password > div > div > div > input")).sendKeys("PLEASE ENTER YOUR PASSWORD FOR THE PARTICULAR ID");
             driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
             driver.findElement(By.id("passwordNext")).click();
             List<WebElement> a = driver.findElements(By.xpath("//*[@class='yW']/span"));
             System.out.println(a.size());
             driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
             for(int i=0;i<a.size();i++){
                 System.out.println(a.get(i).getText());
               
                 if(a.get(i).getText().matches("info@newegg.com"))
                 { 
                     a.get(i).click();
                 }
             }
           driver.findElement(By.partialLinkText("Reset Password Link")).click();
           // Switching Parent Window to Child Window to Reset Password
           Set<String> AllWindowHandles = driver.getWindowHandles();
           String window1 = (String) AllWindowHandles.toArray()[0];
           System.out.print("window1 handle code = "+AllWindowHandles.toArray()[0]);
           String window2 = (String) AllWindowHandles.toArray()[1];
           System.out.print("\nwindow2 handle code = "+AllWindowHandles.toArray()[1]);
           driver.switchTo().window(window2);
           driver.navigate().refresh();
           driver.findElement(By.id("newpassword")).sendKeys("2018@Challenge");
           driver.findElement(By.id("newpassword1")).sendKeys("2018@Challenge");
           driver.findElement(By.cssSelector("#form1 > div > a")).click();
           System.out.println("Password Changed Successfully");
           driver.switchTo().window(window1);

	} catch (FileNotFoundException fnfe) {
		  fnfe.printStackTrace();
		 } catch (IOException ioe) {
		  ioe.printStackTrace();
		 }
   
}
}

