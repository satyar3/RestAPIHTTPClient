package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {

	TestBase testbase;
	RestClient restclient;

	String serviceurl;
	String apiurl;

	String url;
	HashMap<String, String> headerMap;
	
	CloseableHttpResponse closableHttprespoense;

	@BeforeTest
	public void setUp() throws ClientProtocolException, IOException, JSONException {
		testbase = new TestBase();
		serviceurl = prop.getProperty("URL");
		apiurl = prop.getProperty("serviceurl");

		url = serviceurl + apiurl;
	}

	@Test(priority = 1)
	public void getAPITestWithOutHeader() throws ClientProtocolException, IOException, JSONException {
		restclient = new RestClient();
		closableHttprespoense = restclient.get(url);

		// a. Status Code
		int statusCode = closableHttprespoense.getStatusLine().getStatusCode();
		System.out.println("statusCode : " + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 ,"Status code is not 200");

		// b. Json String
		String responseString = EntityUtils.toString(closableHttprespoense.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("responseJson : " + responseJson);
		
		//Per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page"); //Per page value from the response
		System.out.println("value of per_page : "+perPageValue);
		Assert.assertEquals(perPageValue, "3"); 
		
		//Total value
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total"); //Per page value from the response
		System.out.println("value of total : "+totalValue);
		Assert.assertEquals(totalValue, "12"); 
		
		
		//get value from Json Array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avayar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");		
		

		// c. All Headers
		Header[] headerArray = closableHttprespoense.getAllHeaders();
		HashMap<String, String> headerMap = new HashMap<String, String>();

		for (Header header : headerArray) {
			headerMap.put(header.getName(), header.getValue());
		}

		System.out.println("All Headers : " + headerMap);
	}
	
	
	@Test(priority = 2)
	public void getAPITestWithHeader() throws ClientProtocolException, IOException, JSONException {
		restclient = new RestClient();
		
		headerMap = new HashMap<String, String>();
		headerMap.put("user-name", "test1");
		headerMap.put("password", "test1");
		headerMap.put("Content-Type", "application/json");
		
		closableHttprespoense = restclient.get(url, headerMap);

		// a. Status Code
		int statusCode = closableHttprespoense.getStatusLine().getStatusCode();
		System.out.println("statusCode : " + statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 ,"Status code is not 200");

		// b. Json String
		String responseString = EntityUtils.toString(closableHttprespoense.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("responseJson : " + responseJson);
		
		//Per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page"); //Per page value from the response
		System.out.println("value of per_page : "+perPageValue);
		Assert.assertEquals(perPageValue, "3"); 
		
		//Total value
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total"); //Per page value from the response
		System.out.println("value of total : "+totalValue);
		Assert.assertEquals(totalValue, "12"); 
		
		
		//get value from Json Array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avayar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");		
		

		// c. All Headers
		Header[] headerArray = closableHttprespoense.getAllHeaders();
		HashMap<String, String> headerMap = new HashMap<String, String>();

		for (Header header : headerArray) {
			headerMap.put(header.getName(), header.getValue());
		}

		System.out.println("All Headers : " + headerMap);
	}

	@AfterTest
	public void tearDown() {

	}

}
