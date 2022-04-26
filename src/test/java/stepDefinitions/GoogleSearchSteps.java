package stepDefinitions;

import dataProviders.ConfigFileReader;
import helpers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.GoogleSearchPage;
import pageObjects.JPMorganPage;
import utilities.TestContext;

import java.awt.*;

public class GoogleSearchSteps {

    TestContext testContext;
    GoogleSearchPage googleSearchPage;
    JPMorganPage jpMorganPage;
    ConfigFileReader configFileReader;
    DriverManager driverManager;

    public GoogleSearchSteps(TestContext context) {
        testContext = context;
        googleSearchPage = testContext.getPageObjectManager().getGoogleSearchPage();
        jpMorganPage = testContext.getPageObjectManager().getJPMorganPage();
        driverManager = testContext.getDriverManager();
        configFileReader = new ConfigFileReader();
    }

    @Given("Google Search page is displayed")
    public void googleSearchPageIsDisplayed() {
        Assert.assertTrue(googleSearchPage.defaultGoogleSearchPageIsDisplayed());

    }
    @When("Input data with {string} in search bar")
    public void inputDataSearch(String data) {
        googleSearchPage.setSearchInput(data);

    }

    @Then("Verify the {string} data is displayed in the search list")
    public void verifyDataIsDisplayed(String string) {
        googleSearchPage.validateSearchResult();
    }

    @When("Click on first result from the result set list")
    public void clickOnFirstResultFromResultList() throws InterruptedException, AWTException {
        googleSearchPage.clickOnSearchResult();

    }

    @When("Click on first link from results page")
    public void clickOnFirstResultLink() {
        googleSearchPage.clickOnOfficialLink();

    }
    @Then("Verify the page is navigated to application welcome page")
    public void verifyPageNavigation() {
       String appUrl =  driverManager.getCurrentUrl();
        System.out.println("App Url" + appUrl);
        System.out.println(configFileReader.getValidationUrl());
      Assert.assertEquals(appUrl,configFileReader.getValidationUrl());

    }

    @Then("Verify the Logo in Welcome page of J.P.Morgan")
    public void verifyLogo() {
        jpMorganPage.validateLogoImage();
    }



}
