package com.selenium.cucumber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.selenium.cucumber.AutomationApp;

import de.sstoehr.harreader.HarReaderException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AutomationAppTest {

	@Autowired
	private AutomationApp auto;
	
	@Test
	public void testMainPage() throws HarReaderException {
		auto.start();
	}

}
