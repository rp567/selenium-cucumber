package com.selenium.cucumber;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;

@Component
public class AutomationEventListener extends AbstractWebDriverEventListener {
	
	@Value("${short.delay.millis}")
	private long shortDelay;
	
	@Value("${medium.delay.millis}")
	private long mediumDelay;
	
	@Value("${long.delay.millis}")
	private long longDelay;
	
	@Value("${net.export.directory}")
	private String netExportDirectory;
	
	@Value("${net.export.directory.archive}")
	private String netExportDirectoryArchive;
	
	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		
		super.beforeNavigateTo(url, driver);
		
		System.out.println("Before");
		
		try {
			archiveNetExport();
			
			 // Wait till Firebug is loaded
			Thread.sleep(mediumDelay);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		super.afterNavigateTo(url, driver);
		
		System.out.println("After");
		
		try {
			// Have to add a delay or HAR file not created
			Thread.sleep(mediumDelay);
			
			parseNetExport();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done!");
	}
	
	
	private void archiveNetExport() throws IOException {
		
		Collection<File> exportedFiles = FileUtils.listFiles(
				new File(netExportDirectory), new String[] {"har"}, false);
		
		if (!exportedFiles.isEmpty()) {
			FileUtils.moveFileToDirectory(exportedFiles.iterator().next(), 
					new File(netExportDirectoryArchive), true);
		}
		
	}
	
	private void parseNetExport() throws HarReaderException {
		Collection<File> exportedFiles = FileUtils.listFiles(
				new File(netExportDirectory), new String[] {"har"}, false);
		
		HarReader harReader = new HarReader();
		Har har = harReader.readFromFile(exportedFiles.iterator().next());
		
		for (HarEntry harEntry :  har.getLog().getEntries()) {
			System.out.println(harEntry.getRequest().getUrl() + "-->" + harEntry.getResponse().getStatus());
		}
	}
}
