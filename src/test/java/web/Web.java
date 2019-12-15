package web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Web {
    String rootUrl = "https://www.google.com/";
    ChromeDriver chrDrive;
    String elname = "q";
    @BeforeClass
    public void driversSettingUp(){
        System.setProperty("webdriver.chrome.driver","webdrivers/chromedriver.exe");
       chrDrive = new ChromeDriver();
    }

    @Test
    public void openPage()
    {
        chrDrive.get(rootUrl);
        Assert.assertEquals(chrDrive.getTitle(),"Google");

    }
    @Test
    public void writeSmth()
    {
        chrDrive.findElementByName(elname).sendKeys("Wiki");
        chrDrive.findElementByName(elname).sendKeys(Keys.ENTER);
        Assert.assertEquals(chrDrive.getTitle(),"Wiki - Пошук Google");
    }

    @AfterClass
    public void  closeBrowsers()
    {
        chrDrive.quit();
    }
}
