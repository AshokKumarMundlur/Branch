package com.branch.website;

import org.openqa.selenium.By;

import com.utilities.PageHelper;

public class BranchHomePage {

	private By team = By.linkText("Team");
	private By cookiesOptOut = By.id("CybotCookiebotDialogBodyButtonDecline");

	public void navigateToTeamPage() throws Throwable {
		PageHelper.getElement(cookiesOptOut).click();
		PageHelper.elementClickable(PageHelper.scrollTo(team)).click();
	}

}
