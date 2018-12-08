package com.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	protected Properties prop;
	protected int RESPONSE_STATUS_CODE_200 = 200;
	protected int RESPONSE_STATUS_CODE_201 = 201;
	protected int RESPONSE_STATUS_CODE_400 = 400;
	protected int RESPONSE_STATUS_CODE_404 = 404;
	protected int RESPONSE_STATUS_CODE_401 = 401;

	public TestBase() {
		FileInputStream fs;
		try {
			prop = new Properties();
			fs = new FileInputStream("src\\main\\java\\com\\qa\\config\\config.properties");			
			prop.load(fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
