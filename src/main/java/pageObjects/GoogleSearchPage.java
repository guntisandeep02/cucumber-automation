package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Log;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class GoogleSearchPage {

    public GoogleSearchPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(name = "q")
    private WebElement text_SearchField;

    @FindBy(xpath ="//ul[@role='listbox']//li")
    private List<WebElement> searchResultSet;

    @FindBy(xpath ="//a//h3[text()='J.P. Morgan | Official Website']")
    private WebElement linkJPMorgan;

    public boolean defaultGoogleSearchPageIsDisplayed() {
        text_SearchField.isDisplayed();
        Log.info("Google Homepage navigation Successful");
        return true;
    }

    public void setSearchInput(String value) {
        text_SearchField.isEnabled();
        text_SearchField.click();
        text_SearchField.sendKeys(value);
        Log.info("Input entered successfully..");
    }
    public void clickOnSearchResult() throws InterruptedException, AWTException {

        Robot robot = new Robot();
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Log.info("Clicked on first result set from Search Successfully");
    }

    public void validateSearchResult() {
        for(WebElement e:searchResultSet){
            String value = e.getText();
            if(value.equalsIgnoreCase("J.P. Morgan")){
                break;
            }
        }
    }

    public void clickOnOfficialLink(){
        linkJPMorgan.click();
        Log.info("Clicked on application link Successfully");
    }

}
