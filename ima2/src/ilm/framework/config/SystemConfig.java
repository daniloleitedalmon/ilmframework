package ilm.framework.config;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Observable;
import java.util.Properties;
import java.util.ResourceBundle;


public final class SystemConfig extends Observable {

	private Properties _parameters;
	private boolean _isApplet;
    private Locale _currentLocale;
    
	public SystemConfig(boolean isApplet, String[] parameterList) {
		_parameters = new Properties(getDefaultProperties());
		_isApplet = isApplet;
		setProperties(parameterList);
		setLanguage(_parameters.getProperty("language"));
	}

	private Properties getDefaultProperties() {
		ResourceBundle defaultConfig = ResourceBundle.getBundle("ima2.ilm.framework.config.defaultConfig");
        Properties defaultProperties = new Properties();
        
        String s;
        for(Enumeration<String> e = defaultConfig.getKeys(); e.hasMoreElements();) {
            s = e.nextElement();
            defaultProperties.setProperty(s, defaultConfig.getString(s));
        }
        return defaultProperties;
	}
	
	private void setProperties(String[] stringContent) {
        int equalsIndex;
        String key;
        String value;
        
        for(int i = 0; i < stringContent.length; i++) {
            equalsIndex = stringContent[i].lastIndexOf("=");
            
            key = stringContent[i].substring(0, equalsIndex);
            value = stringContent[i].substring(equalsIndex+1);
            
            _parameters.setProperty(key, value);
        }
    }
	
	public void setParameter(String key, String value) {
		_parameters.setProperty(key, value);
		setChanged();
		notifyObservers();
	}

	public void setLanguage(String language) {
		_currentLocale = new Locale(language);
		setChanged();
		notifyObservers();
	}

	public String getValue(String key) {
		return _parameters.getProperty(key);
	}
	
	public Locale getLanguage() {
		return _currentLocale;
	}
	
	public boolean isApplet() {
		return _isApplet;
	}
	
}
