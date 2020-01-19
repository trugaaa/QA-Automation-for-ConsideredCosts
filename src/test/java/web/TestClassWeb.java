package web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;

import web.data.WebData;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestClassWeb {

    WebDriver webDriver;
    WebData webData;

    @BeforeClass
    public void driversSettingUp(){
        webData = new WebData();

        System.setProperty("webdriver.chrome.driver","src/test/java/web/resources/chromedriver.exe");
        webDriver = new ChromeDriver();

        //System.setProperty("webdriver.edge.driver","src/test/java/web/resources/msedgedriver.exe");
        //webDriver = new EdgeDriver();
    }

    @Test
    public void openPage()
    {
        webDriver.get(webData.rootURL);
    }
}
