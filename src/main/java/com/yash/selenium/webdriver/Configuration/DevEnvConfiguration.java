package com.yash.selenium.webdriver.Configuration;


import com.yash.selenium.webdriver.Actions.ConfigurationActions;
import com.yash.selenium.webdriver.constant.ProjectConstant;

public class DevEnvConfiguration implements EnvironmentConfiguration{
	public String getUserName(){
		return ProjectConstant.DEV_USERID;
	}

	public String getURL() {
		return ProjectConstant.DEV_BASEURL;
	}

	public String getPassword() {
		return ProjectConstant.DEV_PASSWORD;
	}
	
	@Override
	public String getBrowesrName() {
		// TODO Auto-generated method stub 
		return ConfigurationActions.getBrowserToRun();
	}
	
}
