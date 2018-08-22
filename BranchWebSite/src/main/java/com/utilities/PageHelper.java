package com.utilities;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.Status;

public class PageHelper {

	private static WebDriver webDriver;

	public PageHelper(WebDriver driver) throws Throwable {
		webDriver = driver;
	}

	/**
	 * Navigate to the URL
	 * @param url
	 * @return Web-driver
	 * @throws Throwable
	 */
	public static WebDriver navigateTo(String url) throws Throwable {
		Assert.assertNotNull(url, "Cannot navigate URL is null");
		Assert.assertTrue("Cannot navigate invalid URL", url.matches("(?)http.*|https.*"));
		webDriver.get(url);
		Reporter.reporterEvent(Status.INFO, "Navigate to URL - " + url + " | Page title is " + webDriver.getTitle());
		return webDriver;
	}

	/**
	 * Wait until the visibility of an element in a page
	 * @param locator
	 * @return Web-element
	 * @throws Throwable
	 */
	public static WebElement waitForElement(By locator) throws Throwable {
		Assert.assertNotNull("Cannot locate, by locator is null", locator);
		return BrowserUtility.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * returns element after the element is visible 
	 * @param locator
	 * @return Web-Element
	 * @throws Throwable
	 */
	public static WebElement getElement(By locator) throws Throwable {
		Assert.assertNotNull("Cannot locate, by locator is null", locator);
		return waitForElement(locator);
	}

	/**
	 * method will get the WebElement based on by locator and scroll to the WebElement
	 * @param locator
	 * @return
	 * @throws Throwable
	 */
	public static WebElement scrollTo(By locator) throws Throwable {
		Assert.assertNotNull("Cannot locate, by locator is null", locator);
		WebElement element = getElement(locator);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		return element;
	}
	
	/**
	 * method will scroll to the WebElement
	 * @param element
	 * @return WebElement
	 * @throws Throwable
	 */
	public static WebElement scrollTo(WebElement element) throws Throwable {
		Assert.assertNotNull("Cannot scroll, WebElement is null", element);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		return element;
	}
	
	/**
	 * method will wait until the element is click-able and return the WebElement
	 * @param element
	 * @return WebElement
	 * @throws Throwable
	 */
	public static WebElement elementClickable(WebElement element) throws Throwable {
		return BrowserUtility.getWait().until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**
	 * method returns the List of WebElements based on the By locator
	 * @param locator
	 * @return List<WebElement>
	 * @throws Throwable
	 */
	public static List<WebElement> getElements(By locator) throws Throwable {
		return webDriver.findElements(locator);	
	}
	
	/**
	 * method to get the text of child element from parent element
	 * @param element
	 * @param byTagName
	 * @return String 
	 * @throws Throwable
	 */
	public static String getText(WebElement element, String byTagName)throws Throwable {
		return element.findElement(By.tagName(byTagName)).getText().trim();
	}
	
	/**
	 * method to mouse over on WebElement
	 * @param element
	 */
	public static void mouseOverOnElement(WebElement element){
		Actions builder = new Actions(webDriver);
		builder.moveToElement(element).build().perform();
	}
}
