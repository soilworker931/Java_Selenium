package framework.utils;

import framework.utils.enums.PropertiesEnum;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ReadProperties {
    private static final Properties PROPERTIES = new Properties();

    public static String readFromConfig(PropertiesEnum property, String key) {
        try {
            InputStream input = new FileInputStream(property.getDirectory());
            PROPERTIES.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PROPERTIES.getProperty(key);
    }
}
