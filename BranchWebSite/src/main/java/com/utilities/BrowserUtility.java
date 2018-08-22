package com.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.ResourceUtils;
import org.testng.Assert;

public class BrowserUtility {
	
	protected WebDriver driver;
	private static long WEBDRIVER_TIME_OUT = 120;
	private static WebDriverWait wait;
	
	/**
	 * launching Fire fox browser and initializing wed and wait driver
	 * @throws Throwable
	 */
	public void launch() throws Throwable{
		System.setProperty("webdriver.gecko.driver", ResourceUtils.getFile("classpath:lib/geckodriver.exe").toString());
		driver = new FirefoxDriver();
		setWait(new WebDriverWait(driver,WEBDRIVER_TIME_OUT));
	}
	
	/**
	 * returns current web driver
	 * @return Web-driver
	 */
	public WebDriver getCurrentDriver(){
		Assert.assertNotNull(driver, "Fire-fox driver not initialized or null");
		return driver;
	}
	
	/**
	 * close browser and quit wed-driver
	 */
	public void closeBrowser(){
		Assert.assertNotNull(driver, "Webdriver not initialized");
		driver.quit();
	}

	/**
	 * returns current web driver 
	 * @return WebDriverWait interface
	 */
	public static WebDriverWait getWait() {
		Assert.assertNotNull(wait, "WebDriverWait object is not initialized");
		return wait;
	}

	/**
	 * sets the web driver time out
	 * @param wait
	 */
	private static void setWait(WebDriverWait wait) {
		BrowserUtility.wait = wait;
	}

}
