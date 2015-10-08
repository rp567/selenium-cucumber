package com.selenium.cucumber;

import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FirefoxProfileFactoryBean implements FactoryBean<FirefoxProfile> {
	
	private static ClassPathResource fireBugResource = new ClassPathResource("firebug-2.0.12.xpi");
	private static ClassPathResource netExportResource = new ClassPathResource("netExport-0.8.xpi");
	
	@Value("${net.export.directory}")
	private String netExportDirectory;
	
	public FirefoxProfile getObject() throws Exception {

		FirefoxProfile profile = new FirefoxProfile();
		
        profile.addExtension(fireBugResource.getFile());
        profile.addExtension(netExportResource.getFile());

        // Set default Firefox preferences
        profile.setPreference("app.update.enabled", false);

        String domain = "extensions.firebug.";

        // Set default Firebug preferences
        profile.setPreference(domain + "currentVersion", "2.0.12");
        profile.setPreference(domain + "showStackTrace", "true");
        profile.setPreference(domain + "allPagesActivation", "on");
        profile.setPreference(domain + "defaultPanelName", "net");
        profile.setPreference(domain + "net.enableSites", true);

        // Set default NetExport preferences
        profile.setPreference(domain + "netexport.alwaysEnableAutoExport", true);
        profile.setPreference(domain + "netexport.autoExportToServer", false);
        profile.setPreference(domain + "netexport.showPreview", true);
        profile.setPreference(domain + "netexport.defaultLogDir", netExportDirectory);
        profile.setPreference(domain + "netexport.sendToConfirmation", false);
        profile.setPreference(domain + "netexport.pageLoadedTimeout", 1500);
        profile.setPreference(domain + "netexport.Automation", true);
        profile.setPreference("extensions.firebug.netexport.saveFiles", false);
        
        return profile;
	}

	public Class<?> getObjectType() {
		return FirefoxProfile.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
