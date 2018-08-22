package com.utilities;

import java.util.List;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class Reporter {
	
	private static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static ExtentTest test;
	
	/**
	 * method to create HTML file to report results with extent report library
	 * @return ExtentHtmlReporter object
	 */
    public synchronized static ExtentHtmlReporter generateReportFile() throws Throwable{
        if(htmlReporter == null){
            String workingDir = System.getProperty("user.dir");
            htmlReporter = new ExtentHtmlReporter(workingDir +"/results/extent.html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return htmlReporter;
    }

    /**
     * method will create test results with Name and description
     * @param testName - name of the test to display in the report
     * @param testDesc - description of the test to display in the report
     */
    public static void creatTest(String testName, String testDesc){
    	Assert.assertNotNull(testName,"Test name cannot be blank");
    	test = extent.createTest(testName, testDesc);
    }
    
    /**
     * method will log information with status in test report
     * @param status - status of a test
     * @param message - description of the test
     */
    public static void reporterEvent(Status status, String message){
    	Assert.assertNotNull(status, "Status cannot be blank");
    	test.log(status, message);
    }
    
    /**
     * method will generate data table and log test status
     * @param status - status of a test
     * @param description - description of the test
     * @param data - 2D array to createTable
     * 
     */
    public static void reporterEvent(Status status, String description, String[][] data){
    	Assert.assertNotNull(status, "Status cannot be blank");
    	Assert.assertNotNull(data,"Data parameter cannot be blank");
    	generateTable(status, description, data);
    }
    
    /**
     * method will convert list to 2D array, generate data table and log test status
     * @param status - status of a test
     * @param description - description of the test
     * @param data - 2D array to createTable
     */
    public static void reporterEvent(Status status,  String description,  List<String> data){
    	Assert.assertNotNull(status, "Status cannot be blank");
    	Assert.assertNotNull(data,"Data parameter cannot be blank");
    	String[][] createTable = data.stream().map(i -> new String[]{i}).toArray(String[][]::new);
    	generateTable(status, description, createTable);
    }
    
    /**
     * method to generate data table and log test status
     * @param status - status of a test
     * @param description - description of the test
     * @param data - 2D array to createTable
     */
    private static void generateTable(Status status, String description, String[][] data){
    	ExtentTest node = test.createNode(description);
    	switch(status.toString()){
    	case"fail":
    		node.fail(MarkupHelper.createTable(data));
	    	break;
    	case"info":
    		node.info(MarkupHelper.createTable(data));
    		break;
    	case"pass":
    		node.pass(MarkupHelper.createTable(data));
    		break;
    	}
    }
    
    /**
     * method must be called after test or after suit to generate extent report
     */
    public static void generateReport(){
    	extent.flush();
    }
}
