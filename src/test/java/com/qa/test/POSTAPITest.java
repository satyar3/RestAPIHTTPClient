package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.testdata.Users;

public class POSTAPITest extends TestBase {

	String serviceurl;
	String apiurl;

	String url;

	RestClient restclient;
	HashMap<String, String> headerMap;

	public POSTAPITest() {
		super();
	}

	@BeforeTest
	public void steUp() {
		serviceurl = prop.getProperty("URL");
		apiurl = prop.getProperty("serviceurl");

		url = serviceurl + apiurl;
	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException, JSONException {

		restclient = new RestClient();

		headerMap = new HashMap<String, String>();
		headerMap.put("userName", "test123");
		headerMap.put("password", "test123");
		headerMap.put("Content-Type", "application/json");

		// Jackson API to convert java object to json object  ---> Masrshelling

		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("testuser", "leader");
		mapper.writeValue(new File("src\\main\\java\\com\\qa\\testdata\\users.json"), users);

		// JsonObject to String
		String userJasonString = mapper.writeValueAsString(users);

		CloseableHttpResponse httpresponse = restclient.post(url, userJasonString, headerMap);

		// 1. Status code
		int statuscode = httpresponse.getStatusLine().getStatusCode();
		System.out.println("statuscode : " + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_201);

		// 2. Jason String
		String responseString = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		JSONObject jsonobject = new JSONObject(responseString);
		System.out.println("jsonobject : " + jsonobject);

		// Json to java object  ----> Unmarshelling
		Users obj = mapper.readValue(responseString, Users.class);

		Assert.assertEquals(users.getName(), obj.getName()); // expected vs actual
		Assert.assertEquals(users.getJob(), obj.getJob()); // expected vs actual

		// 3. All Header
		Header[] headerArray = httpresponse.getAllHeaders();
		HashMap<String, String> hashmap = new HashMap<String, String>();

		for (Header header : headerArray) {
			hashmap.put(header.getName(), header.getValue());
		}

		System.out.println(hashmap);

	}

}
