package com.google.search;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.utilities.PageHelper;

public class GoogleSearchPage {
	
	private By googleSearch = By.name("q");
	private By branchWebSiteLink = By.linkText("Branch.io");
	private final String googleSearchPage = "https://www.google.com/";
	
	
	/**
	 * navigate to google search page
	 * @throws Throwable 
	 */
	public void navigateToSearchPage() throws Throwable{
		PageHelper.navigateTo(googleSearchPage);
	}
	
	/**
	 * method to search on google search page	
	 * @param value
	 * @throws Throwable
	 */
	public void search(String value) throws Throwable{
		Assert.assertNotNull(value, "Search value cannot be blank");
		PageHelper.getElement(googleSearch).sendKeys(value, Keys.ENTER);
	}
	
	/**
	 * Navigate to Branch web site from search results page
	 */
	public void openBranchSite() throws Throwable{
		PageHelper.getElement(branchWebSiteLink).click();
	}
	
}
