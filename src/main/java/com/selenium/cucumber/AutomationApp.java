package com.selenium.cucumber;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.sstoehr.harreader.HarReaderException;

@Component
public class AutomationApp {
	
	@Autowired
	private WebDriverEventListener webDriverEventListener;
	
	@Autowired
	private AutomationUtils autoUtils;
	
	@Value("${application.url}")
	private String websiteMainURL;
	
	@Autowired
	private FirefoxProfileFactoryBean profileFactoryBean;
	
	public void start() throws HarReaderException {
        
        WebDriver firefoxDriver = null;
		
        try {
			firefoxDriver = new FirefoxDriver(profileFactoryBean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(firefoxDriver);
        eventFiringWebDriver.register(webDriverEventListener);
        eventFiringWebDriver.get(websiteMainURL);
        
        if (autoUtils.parseNetExport()) {
        	//next call
        	System.out.println("Checking condition");
        	
        	WebElement search = eventFiringWebDriver.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div[2]/div[1]/div[1]/div[3]/div/div[3]/div/input[1]"));
        	search.sendKeys("hello");
        	
        };
        
        eventFiringWebDriver.close();
        eventFiringWebDriver.quit();
        
    }

}
