package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesFile {

    public static String loadBaseUrl() {

        try (InputStream input = new FileInputStream("resources/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("STAGING");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";



    }


}
