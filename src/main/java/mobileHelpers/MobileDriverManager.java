package mobileHelpers;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utilities.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;


/**
 * contains all the methods to create a new session and destroy the 
 * session after the test(s) execution is over. Each test extends
 *  this class.
 */
public class MobileDriverManager {

	public WebDriver driver = null;
	protected static Properties appConfigProp = new Properties();
	protected FileInputStream configFis, appConfigFis;
	public Properties testDataFile;
	private final String CONFIG_FILE_PATH=System.getProperty("user.dir")+"\\config\\configuration.properties";
	protected File file = new File("PATH NAME");
	Properties configProp = new Properties();
	String OS;


	/** 
	 * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
	 * @throws Exception Unable to start appium server
	 */
	//@BeforeSuite
	public void invokeAppium() throws Exception
	{
		String OS = System.getProperty("os.name").toLowerCase();
		try{
			startAppiumServer(OS);
			Log.info("Appium server started successfully");
		}
		catch (Exception e) {
			Log.logError(getClass().getName(), "startAppium", "Unable to start appium server");
			throw new Exception(e.getMessage());
		}
	}

	/** 
	 * this method stops Appium server.Calls stopAppiumServer method to 
	 * stop session depending upon your OS.
	 * @throws Exception Unable to stop appium server
	 */
	//@AfterSuite
	public void stopAppium() throws Exception {
		try{
			stopAppiumServer(OS);
			Log.info("Appium server stopped successfully");

		}
		catch (Exception e) {
			Log.logError(getClass().getName(), "stopAppium", "Unable to stop appium server");
			throw new Exception(e.getMessage());
		}
	}


	/** 
	 * this method creates the driver depending upon the passed parameter (android or iOS)
	 *  and loads the properties files (config and test data properties files).
	 * @param os android or iOS
	 * @param methodName - name of the method under execution  
	 * @throws Exception issue while loading properties files or creation of driver.
	 */
	//@Parameters({"os"})
	//@BeforeMethod
	public  void createDriver(String os,Method methodName) throws Exception{

		propertiesFileLoad(os);

		File propertiesFile = new File(file.getAbsoluteFile() + "//src//test//log4j.properties");
		PropertyConfigurator.configure(propertiesFile.toString());
		Log.info("--------------------------------------");

		if (os.equalsIgnoreCase("android")){
			String buildPath = choosebuild(os);
			androidDriver(buildPath, methodName);
			Log.info("Android driver created");

		}																		         
		else if (os.equalsIgnoreCase("iOS")){
			String buildPath = choosebuild(os);
			iOSDriver(buildPath, methodName);
			Log.info("iOS driver created");
		}
	}

	/** 
	 * this method quit the driver after the execution of test(s) 
	 */
	//@AfterMethod
	public void teardown(){
		Log.info("Shutting down driver");
		driver.quit();
	}



	/** 
	 *  this method creates the android driver
	 *  @param buildPath - path to pick the location of the app
	 *  @param methodName - name of the method under execution 
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
	 */
	public void androidDriver(String buildPath, Method methodName) throws MalformedURLException{
		File app = new File(buildPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability("appPackage", "APP package name to be provided here");
		capabilities.setCapability("appActivity", "APP activity to be provided here");
		capabilities.setCapability("name", methodName.getName());
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("automationName", "UiAutomator2");
		//driver = new AndroidDriver( new URL("http://localhost:4723/wd/hub"), capabilities);

	}

	/** 
	 *  this method creates the iOS driver
	 *  @param buildPath- path to pick the location of the app
	 *  @param methodName- name of the method under execution
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
	 */
	public void iOSDriver(String buildPath, Method methodName) throws MalformedURLException {
		File app = new File(buildPath);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName","iOS");
		capabilities.setCapability("platformVersion", "15.2");
		capabilities.setCapability("appiumVersion", "1.16.0");
		capabilities.setCapability("name", methodName.getName());
		capabilities.setCapability("app", app.getAbsolutePath());
		//driver  = new IOSDriver( new URL("http://localhost:4723/wd/hub"), capabilities);

	}

	/** 
	 *  this method starts the appium  server depending on your OS.
	 * @param os your machine OS (windows/linux/mac)
	 * @throws IOException Signals that an I/O exception of some sort has occurred
	 * @throws ExecuteException An exception indicating that the executing a subprocesses failed
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied, and the thread is interrupted, either before
	 *  or during the activity.
	 */
	public void startAppiumServer(String os) throws ExecuteException, IOException, InterruptedException{
		if (os.contains("windows")){
			CommandLine command = new CommandLine("cmd");  
			command.addArgument("/c");  
			command.addArgument("C:/Program Files/nodejs/node.exe");  
			command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");  
			command.addArgument("--address", false);  
			command.addArgument("127.0.0.1");  
			command.addArgument("--port", false);  
			command.addArgument("4723");  
			command.addArgument("--full-reset", false);  

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();  
			DefaultExecutor executor = new DefaultExecutor();  
			executor.setExitValue(1);  
			executor.execute(command, resultHandler);  
			Thread.sleep(5000);  
		}
		else{
			Log.info(os + "is not supported yet");
		}
	}

	/** 
	 *  this method stops the appium  server.
	 * @param os your machine OS (windows/linux/mac).
	 * @throws IOException Signals that an I/O exception of some sort has occurred. 
	 * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
	 */
	public void stopAppiumServer(String os) throws ExecuteException, IOException {
		if (os.contains("windows")){
			CommandLine command = new CommandLine("cmd");  
			command.addArgument("/c");  
			command.addArgument("Taskkill /F /IM node.exe");  

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();  
			DefaultExecutor executor = new DefaultExecutor();  
			executor.setExitValue(1);  
			executor.execute(command, resultHandler);  
		}
		else if (os.contains("mac os x")){
			String[] command ={"/usr/bin/killall","-KILL","node"};  
			Runtime.getRuntime().exec(command);  
			Log.info("Appium server stopped");  
		}
		else if (os.contains("linux")){
			// need to add it
		}
	}

	/**
	 *  this method loads properties files config and file having test data.
	 * @param platform android or ios, to load specific test data file.
	 * @throws Exception property files are not loaded successfully
	 */
	public void propertiesFileLoad(String platform) throws Exception{
		configFis = new FileInputStream(file.getAbsoluteFile()
				+ CONFIG_FILE_PATH);
		configProp .load(configFis);

		File f = new File(file.getAbsoluteFile() + "\\config\\configuration.properties");

		if (f.exists() && !f.isDirectory()) {
			appConfigFis = new FileInputStream(file.getAbsoluteFile() + "\\config\\configuration.properties");
			appConfigProp.load(appConfigFis);
		}
		else {
			throw new Exception("Properties files loading failed ");
		}}

	public String choosebuild(String invokeDriver){
		String appPath = null;
		if(invokeDriver.equals("android")){
			appPath = configProp.getProperty("AndroidAppPath");
			return appPath;
		}
		else if(invokeDriver.equals("iOS")){
			appPath = configProp.getProperty("iOSAppPath");
			return appPath;
		}

		return appPath;
	}


	public WebDriver getWebDriver(){
		return this.driver;
	}



}

