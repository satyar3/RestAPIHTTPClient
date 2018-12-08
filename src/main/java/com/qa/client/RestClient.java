package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

public class RestClient {

	// 1. GET Method without header
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // Http get request

		CloseableHttpResponse closableHttprespoense = httpClient.execute(httpget); // hit the get url

		return closableHttprespoense;
	}

	// 2. GET Method with header
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException, JSONException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // Http get request

		// Passing header
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closableHttprespoense = httpClient.execute(httpget); // hit the get url

		return closableHttprespoense;
	}

	// 3. POST Method
	public CloseableHttpResponse post(String url, String entityset, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		httppost.setEntity(new StringEntity(entityset)); // for json payload

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse httpResponse = httpClient.execute(httppost);

		return httpResponse;
	}

}
