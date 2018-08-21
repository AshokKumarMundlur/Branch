package com.utilities;

import java.util.List;

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
	 * 
	 * @return
	 */
    public synchronized static ExtentHtmlReporter generateReportFile(){
        if(htmlReporter == null){
            String workingDir = System.getProperty("user.dir");
            htmlReporter = new ExtentHtmlReporter(workingDir +"/results/extent.html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return htmlReporter;
    }

    
    public static void creatTest(String testName, String testDesc){
    	test = extent.createTest(testName, testDesc);
    }
    
    public static void reporterEvent(Status status, String message){
    	test.log(status, message);
    }
    
    public static void reporterEvent(Status status, String description, String[][] createTable){
    	generateTable(status, description, createTable);
    }
    
    public static void reporterEvent(Status status,  String description,  List<String> data){
    	String[][] createTable = data.stream().map(i -> new String[]{i}).toArray(String[][]::new);
    	generateTable(status, description, createTable);
    }
    
    private static void generateTable(Status status, String description, String[][] createTable){
    	ExtentTest node = test.createNode(description);
    	switch(status.toString()){
    	case"fail":
    		node.fail(MarkupHelper.createTable(createTable));
	    	break;
    	case"info":
    		node.info(MarkupHelper.createTable(createTable));
    		break;
    	case"pass":
    		node.pass(MarkupHelper.createTable(createTable));
    		break;
    	}
    }
    public static void generateReport(){
    	extent.flush();
    }
}
