package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Log;

public class JPMorganPage {

    WebDriver driver;
    public JPMorganPage(WebDriver webDriver) {
        this.driver=webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//div[@class='logo-desktop-only']//img[@alt='J.P. Morgan logo']")
    private WebElement logoImage;

    public boolean validateLogoImage(){

       String logoText = logoImage.getAttribute("alt");
       if(logoText.contains("J.P. Morgan")){
           Log.info("Logo Present with Text J.P. Morgan");
       }else{
           Log.logError(this.getClass().getName(), "validateLogoImage", "Logo not present with Text J.P. Morgan");
       }
        Boolean imageLogo = (Boolean) ((JavascriptExecutor)driver) .executeScript("return arguments[0].complete " + "&& typeof arguments[0].naturalWidth != \"undefined\" " + "&& arguments[0].naturalWidth > 0", logoImage);

        //verify if status is true
        if (imageLogo) {
            Log.info("Logo present in the application");
        } else {
            Log.logError(this.getClass().getName(), "validateLogoImage", "Element not found");
        }
        return true;
    }

}
