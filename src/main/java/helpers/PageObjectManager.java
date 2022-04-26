package helpers;

import pageObjects.*;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private final WebDriver webDriver;
    private GoogleSearchPage googleSearchPage;
    private JPMorganPage jpMorganPage;

    public PageObjectManager(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public GoogleSearchPage getGoogleSearchPage() {

        if (googleSearchPage == null) {
            googleSearchPage = new GoogleSearchPage(webDriver);
        }
        return googleSearchPage;
    }

    public JPMorganPage getJPMorganPage() {

        if (jpMorganPage == null) {
            jpMorganPage = new JPMorganPage(webDriver);
        }
        return jpMorganPage;
    }
}
