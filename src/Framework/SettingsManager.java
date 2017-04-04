package Framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
	private static SettingsManager instance = null;
	private String settingsPath = "/settings";
	private String settingsFileName = "settings.cfg";
	static Properties mySettings = null;
	
	private SettingsManager(){
		
		mySettings = new Properties();
		try {
			String path ="./" + settingsPath + "/" + settingsFileName;
			mySettings.load(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getSetting(String what){
		if( null == instance ){
			instance = new SettingsManager();
		}
		
		if( null != mySettings ){
			return mySettings.getProperty(what);
		}
		return null;
	}
	
}
