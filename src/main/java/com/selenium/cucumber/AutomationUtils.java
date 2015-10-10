package com.selenium.cucumber;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;

@Component
public class AutomationUtils {
	
	@Value("${net.export.directory}")
	private String netExportDirectory;	
	
	public boolean parseNetExport() throws HarReaderException {
		Collection<File> exportedFiles = FileUtils.listFiles(
				new File(netExportDirectory), new String[] {"har"}, false);
		
		HarReader harReader = new HarReader();
		Har har = harReader.readFromFile(exportedFiles.iterator().next());
		HarEntry last = har.getLog().getEntries().get(har.getLog().getEntries().size() - 1);
		
		for (HarEntry harEntry :  har.getLog().getEntries()) {
			System.out.println(harEntry.getRequest().getUrl() + "-->" + harEntry.getResponse().getStatus());
		}
		
		return last.getResponse().getStatus() == 200;
	}

}
