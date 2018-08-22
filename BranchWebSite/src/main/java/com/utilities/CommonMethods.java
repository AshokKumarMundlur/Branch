package com.utilities;

import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.Assert;

public class CommonMethods {
	
	/**
	 * returns the URL HTTP status code
	 * @param url
	 * @return integer - status code
	 * @throws Throwable
	 */
	public static int validateHttpStatusCode(String url) throws Throwable{
		Assert.assertNotNull(url, "URL cannot be null");
		URL httpUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)httpUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        return connection.getResponseCode();
        
	}
}
