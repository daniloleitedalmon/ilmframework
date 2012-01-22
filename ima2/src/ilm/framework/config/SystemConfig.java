package ilm.framework.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;


public final class SystemConfig extends Observable {

	private static final String CUSTOM_PROPERTIES_KEY = "config";
	private static final String DEFAULT_PROPERTIES_FILE = "DefaultConfig.properties";
	private Properties _parameters;
	private boolean _isApplet;
    private Locale _currentLocale;
    
	public SystemConfig(boolean isApplet, Map<String, String> parameterList) throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		this(isApplet, parameterList, null);
	}
	
	public SystemConfig(boolean isApplet, Map<String, String> parameterList, Properties properties) throws InvalidPropertiesFormatException, FileNotFoundException, IOException
	{
		if(properties != null)
		{
			_parameters = properties;
		}
		else
		{
			if(parameterList.containsKey(CUSTOM_PROPERTIES_KEY))
			{
				_parameters = new Properties();
				_parameters.loadFromXML(new FileInputStream(parameterList.get(CUSTOM_PROPERTIES_KEY)));
			}
			else
			{
				_parameters = getDefaultProperties();
			}
		}
		_isApplet = isApplet;
		setProperties(parameterList);
		setLanguage(_parameters.getProperty("language"));
	}

	private Properties getDefaultProperties() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
        Properties defaultProperties = new Properties();
        defaultProperties.loadFromXML(new FileInputStream(DEFAULT_PROPERTIES_FILE));

        return defaultProperties;
	}
	
	private void setProperties(Map<String, String> parameterList) {        
        for(Map.Entry<String, String> entry : parameterList.entrySet()) {
            _parameters.setProperty(entry.getKey(), entry.getValue());
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
