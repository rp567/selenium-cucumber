package com.selenium.cucumber;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AutomationApp {
	
	@Autowired
	private WebDriverEventListener webDriverEventListener;
	
	@Value("${application.url}")
	private String websiteMainURL;
	
	@Autowired
	private FirefoxProfileFactoryBean profileFactoryBean;
	
	public void start() {
        
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
        
        eventFiringWebDriver.close();
        eventFiringWebDriver.quit();
        
    }

}
