package web.data;

import java.io.FileInputStream;
import java.util.Properties;

public class WebData {
    public String rootURL;

    public WebData() {
        {
            FileInputStream fis;
            Properties property = new Properties();
            try {
                fis = new FileInputStream("src/test/java/web/resources/configWeb.properties");
                property.load(fis);
                rootURL = property.getProperty("ROOT_URL");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
