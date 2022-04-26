package utilities;

import helpers.DriverManager;
import helpers.PageObjectManager;

public class TestContext {

    private final DriverManager driverManager;
    private final PageObjectManager pageObjectManager;
    public ScenarioContext scenarioContext;

    public TestContext() {
        driverManager = new DriverManager();
        pageObjectManager = new PageObjectManager(driverManager.getDriver());
        scenarioContext = new ScenarioContext();
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
