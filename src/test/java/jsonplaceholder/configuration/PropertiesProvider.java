package jsonplaceholder.configuration;

import com.common.framework.configuration.SystemVariablesProvider;
import com.common.framework.utils.FileUtils;

import java.util.Properties;

public class PropertiesProvider {

    private static Properties properties;

    private PropertiesProvider() {
    }

    private static Properties getInstance() {
        if (properties == null) {
            properties = FileUtils.loadFromProperties(SystemVariablesProvider.getEnvironmentValue());
        }
        return properties;
    }

    public static String getPropertyValue(String propertyName) {
        return getInstance().getProperty(propertyName);
    }

}
