package com.testmethods;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.branch.website.BranchHomePage;
import com.branch.website.TeamPage;
import com.google.search.GoogleSearchPage;
import com.utilities.BrowserUtility;
import com.utilities.PageHelper;
import com.utilities.Reporter;

public class TestClass {

	BrowserUtility browser;
	TeamPage teamPage;

	@BeforeTest
	public void beforeTest() throws Throwable {
		Reporter.generateReportFile();
		browser = new BrowserUtility();
		browser.launch();
		new PageHelper(browser.getCurrentDriver());
		
	}

	@Test(priority = 1)
	public void searchBranchWebsite() throws Throwable {
		Reporter.creatTest("Search for Branch Website", "Navigate to google.com and search for branch website");
		GoogleSearchPage google = new GoogleSearchPage();
		google.navigateToSearchPage();
		google.search("Branch.io");
		google.openBranchSite();	
		BranchHomePage branchHomePage = new BranchHomePage();
		branchHomePage.navigateToTeamPage();
		
	}

	@Test(priority = 2, dependsOnMethods = "searchBranchWebsite")
	public void validateEmployeeCount() throws Throwable {
		Reporter.creatTest("Validate Employee count",
				"Verify that number of employees match between ALL tab and sum of other tabs.");
		teamPage = new TeamPage();
		teamPage.getOtherTabsEmpDetails();
		teamPage.getAllTabEmpDetails();
		teamPage.validateEmployeeCount();
	}

	@Test(priority = 3, dependsOnMethods = "validateEmployeeCount")
	public void validateEmployeeNames() throws Throwable {
		Reporter.creatTest("Validate Employee Names",
				"Verify that employee names match between All tab and other tabs.");
		teamPage.validateEmpNamesByDepartment("Data").validateEmpNamesByDepartment("Engineering")
				.validateEmpNamesByDepartment("Marketing").validateEmpNamesByDepartment("Operations")
				.validateEmpNamesByDepartment("Partner Growth").validateEmpNamesByDepartment("Product")
				.validateEmpNamesByDepartment("Recruiting");
	}
	
	@Test(priority = 4, dependsOnMethods = "validateEmployeeCount")
	public void validateDepartmentNames() throws Throwable {
		Reporter.creatTest("Validate Department Names",
				"Verify that employee departments are listed correctly between All tab and Department tabs.");
		teamPage.validateDepartmentNames();
	}
	
	@Test(priority = 5, dependsOnMethods = "validateEmployeeCount")
	public void validateEmployeeBrokenImage() throws Throwable{
		Reporter.creatTest("Validate Broken Images",
				"Verify that images are not broken in ALL tab");
		teamPage.validateBrokenImage();
	}
	
	@Test(priority = 7, dependsOnMethods = "validateEmployeeCount")
	public void validateEmployeeOverlayInfo() throws Throwable{
		Reporter.creatTest("Validate employees overlay info in ALL tab",
				"Verify that overlay info displayed for each employee image");
		teamPage.validateEmpOverLayInfo();
	}
	
	@Test(priority = 6, dependsOnMethods = "validateEmployeeCount")
	public void validateURLHttpStatusCode() throws Throwable{
		Reporter.creatTest("Validate HTTP status code",
				"Verify that HTTP status code is greater then 200");
		teamPage.validateURLHttpStatus();
	}

	@AfterTest
	public void afterTest() {
		browser.closeBrowser();
	}

	@AfterSuite
	public void afterSuite() {
		Reporter.generateReport();
	}

}
