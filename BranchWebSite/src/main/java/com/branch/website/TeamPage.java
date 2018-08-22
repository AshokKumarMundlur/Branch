package com.branch.website;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.utilities.CommonMethods;
import com.utilities.PageHelper;
import com.utilities.Reporter;

public class TeamPage {

	private By waper = By.className("wrap");
	private By infoBlocks = By.className("info-block");
	private By imageBlocks = By.className("image-block");
	private By employeeNameLabel = By.tagName("h2");
	
	//X-path to fetch employee names based on department
	private String departmentLabels = "//div[@class='info-block']/h4[contains(text(),'Department')]/preceding-sibling::h2";
	
	//Other department tab labels
	private String[] tabHeaders = { "Data", "Engineering", "Marketing", "Operations", "Partner Growth", "Product",
			"Recruiting" };
	
	//Below map object will store employee with department names. 
	//Department names as keys and names as list
	private Map<String, List<String>> otherTabsEmpDetails = new LinkedHashMap<>();
	private Map<String, List<String>> allTabEmpDetails = new LinkedHashMap<>();

	private int otherTabsEmpCount;
	
	/**
	 * Get employee details from other tabs
	 * @throws Throwable
	 */
	public void getOtherTabsEmpDetails() throws Throwable {
		for (String tabName : tabHeaders) {
			List<String> employeeNames = new ArrayList<>();
			PageHelper.getElement(By.linkText(tabName.toUpperCase())).click();
			PageHelper.getElements(infoBlocks).forEach(e -> {
				if (e.findElement(employeeNameLabel).getSize().height > 0) {
					String name = e.findElement(employeeNameLabel).getText().trim();
					employeeNames.add(name);
				}
			});
			otherTabsEmpDetails.put(tabName, employeeNames);
			// storying employee count of other tabs
			setOtherTabsEmpCount(getOtherTabsEmpCount() + otherTabsEmpDetails.get(tabName).size());
		}
		Reporter.reporterEvent(Status.INFO, "Getting employee names from other tabs");
	}
	
	/**
	 * Get employee details from ALL tab
	 * @throws Throwable
	 */
	public void getAllTabEmpDetails() throws Throwable {
		PageHelper.getElement(By.linkText("ALL")).click();
		for (String tabName : tabHeaders) {
			List<String> employeeNames = new ArrayList<>();
			PageHelper.getElements(By.xpath(departmentLabels.replace("Department", tabName))).forEach(e -> {
				String name = e.getText().trim();
				employeeNames.add(name);
			});
			allTabEmpDetails.put(tabName, employeeNames);
		}
		Reporter.reporterEvent(Status.INFO, "Getting employee names from ALL tab");
	}

	/**
	 * method to validate employee names between ALL tab and Other tabs by department 
	 * @param department
	 * @return Team page object
	 */
	public TeamPage validateEmpNamesByDepartment(String department) {
		List<String> empNamesByDeptAllTab = allTabEmpDetails.get(department);
		List<String> empNamesByDeptOtherTabs = otherTabsEmpDetails.get(department);
		Collections.sort(empNamesByDeptAllTab);
		Collections.sort(empNamesByDeptOtherTabs);
		//comparing employee names between ALL tab and Other tabs 
		if (empNamesByDeptAllTab.containsAll(empNamesByDeptOtherTabs)) {
			Reporter.reporterEvent(Status.PASS,
					"Employee names are matching with '" + department.toUpperCase() + "' tab and ALL tab",
					empNamesByDeptAllTab);
		} else {
			//filtering non matching employee names and reporting
			empNamesByDeptAllTab.addAll(empNamesByDeptOtherTabs);
			empNamesByDeptAllTab.removeAll(empNamesByDeptOtherTabs);
			Reporter.reporterEvent(Status.FAIL,
					"Employee names not matching  with '" + department.toUpperCase() + "' tab and ALL tab",
					empNamesByDeptAllTab);
		}
		return this;
	}
	
	/**
	 * method to validate department names between ALL tab and Other tabs by department 
	 * @param department
	 * @return Team page object
	 */
	public void validateDepartmentNames() {
		List<String> deptNamesAllTab = allTabEmpDetails.keySet().stream().collect(Collectors.toList());
		List<String> deptNamesOtherTabs = otherTabsEmpDetails.keySet().stream().collect(Collectors.toList());
		if (deptNamesAllTab.equals(deptNamesAllTab)) {
			Reporter.reporterEvent(Status.PASS, "All department names are matching with other tabs", deptNamesAllTab);
		} else {
			deptNamesAllTab.addAll(deptNamesOtherTabs);
			deptNamesAllTab.removeAll(deptNamesOtherTabs);
			Reporter.reporterEvent(Status.FAIL, "All department names are NOT matching with other tabs",
					deptNamesAllTab);
		}
	}

	/**
	 * method to validate employee count between ALL tab and other tabs
	 * @throws Throwable
	 */
	public void validateEmployeeCount() throws Throwable {
		PageHelper.getElement(By.linkText("ALL")).click();
		int allTabEmpCount = getAllTabEmpCount();
		int otherTabsEmpCount = getOtherTabsEmpCount();
		if (allTabEmpCount == otherTabsEmpCount) {
			Reporter.reporterEvent(Status.PASS, "Sum of ALL and Other tabs matching");
		} else {
			Reporter.reporterEvent(Status.FAIL, "Employee count NOT mathcing. ALL tab count is " + allTabEmpCount
					+ " and other tabs count " + otherTabsEmpCount);
		}
	}

	/**
	 * Get employee count of all other tabs
	 * @return 
	 */
	public int getOtherTabsEmpCount() {
		return otherTabsEmpCount;
	}

	/**
	 * Set employee count of all other tabs at run time
	 * @return 
	 */
	private void setOtherTabsEmpCount(int otherTabsEmpCount) {
		this.otherTabsEmpCount = otherTabsEmpCount;
	}

	/**
	 * Get employee count of ALL tab
	 * @return
	 * @throws Throwable
	 */
	public int getAllTabEmpCount() throws Throwable {
		return PageHelper.getElements(infoBlocks).size();
	}

	/**
	 * method to validate Broker images in ALL tab 
	 * @throws Throwable
	 */
	public void validateBrokenImage() throws Throwable {
		List<String> images = new ArrayList<>();
		boolean brokenImg = false;
		for (WebElement image : PageHelper.getElements(imageBlocks)) {
			//validating height of the image is 200   
			if (image.getSize().height != 200)
				brokenImg = true;
			images.add(image.getAttribute("style"));
		}
		if (brokenImg) {
			Reporter.reporterEvent(Status.FAIL, "Images height is not 200", images);
		} else {
			Reporter.reporterEvent(Status.PASS, "All images height is 200 in ALL tab", images);
		}
	}
	
	/**
	 * method to validate each employee overlay information.
	 * @throws Throwable
	 */
	public void validateEmpOverLayInfo() throws Throwable {
		String[][] empOverLayInfo = new String[2][1];
		//Collecting employee blocks in ALL Tab
		for (WebElement empInfo : PageHelper.getElements(waper)) {
			WebElement imgBlock = empInfo.findElement(imageBlocks);
			//scrolling to image and mouse over on image block
			PageHelper.scrollTo(imgBlock);
			PageHelper.mouseOverOnElement(imgBlock);
			//validating Overlay information for each employee and reporting to results report
			WebElement overlayBlock = empInfo.findElement(By.tagName("p"));
			String overlay = "Empty";
			if (overlayBlock.getSize().height > 0) {
				overlay = overlayBlock.getText();
			}
			//Generate data table in results report.
			String name = empInfo.findElement(By.tagName("h2")).getText();
			empOverLayInfo[0][0] = name;
			empOverLayInfo[1][0] = overlay;
			if (overlay.equalsIgnoreCase("Empty")) {
				Reporter.reporterEvent(Status.FAIL, "Validation "+name+" overlay info", empOverLayInfo);
			} else {
				Reporter.reporterEvent(Status.PASS, "Validation "+name+" overlay info", empOverLayInfo);
			}
		}
	}
	/**
	 * method to validate URL HTTP status is grater then 200
	 * @throws Throwable
	 */
	public void validateURLHttpStatus() throws Throwable {
		for (WebElement link : PageHelper.getElements(By.tagName("a"))) {
			String url = link.getAttribute("href");
			if (url != null && url.startsWith("https://branch.io")) {
				int statusCode = CommonMethods.validateHttpStatusCode(url);
				if (statusCode > 200) {
					Reporter.reporterEvent(Status.FAIL,
							link.getText() + "|" + url + "|" + "Status Code : " + statusCode);
				} else {
					Reporter.reporterEvent(Status.PASS,
							link.getText() + "|" + url + "|" + "Status Code : " + statusCode);
				}
			}
		}
	}

}
