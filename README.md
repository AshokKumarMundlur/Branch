# Branch
Branch web site automation

Developed/Implemented with Java, using Maven, TestNG, Selenium WebDriver and Page Objects

Test case# 1
1. Navigate to google.com and search for branch website
2. Navigate to branch website
3. Scroll down to footnotes and click on "Team" link
4. Team page has tabs: All tab(that lists all employees) and department tabs(lists employees by
their departments)
  - Implemented in test method searchBranchWebsite() 

Test case# 2 - Verify that number of employees match between All tab and sum of other tabs.
  - Implemented in test method validateEmployeeCount() 

Test case# 3 - Verify that employee names match between All tab and other tabs.
  - Implemented in test method validateEmployeeNames() 

Test case # 4 - Verify that employee departments are listed correctly between All tab and Department tabs.
Implemented in test method validateDepartmentNames() 

Test case # 5 - Come up with 2 more valuable test cases.
    - implemented in test method validateEmployeeBrokenImage()
    - implemented in test method validateURLHttpStatusCode()
    
Test case # 6 - Come up with test that will fail and add explanation of failure as part of report
  - Implemented in test method validateEmployeeOverlayInfo()
