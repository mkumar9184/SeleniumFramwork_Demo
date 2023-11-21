package com.yash.selenium.webdriver.Configuration;

import com.yash.selenium.webdriver.Actions.ConfigurationActions;

public class QualEnvConfiguration implements EnvironmentConfiguration {
	public String getURL()
    {
    //    return AWS_Devl_BASEURL;
    	return ConfigurationActions.getURLToRun();
    }

	
    public String getPassword()
    {
    //    return AWS_Devl_PASSWORD;
    	return ConfigurationActions.getPasswordToRun();
    }

    public String getUserName()
    {
     //   return AWS_Devl_USERID;
    	return ConfigurationActions.getUserNameToRun();
    }


	@Override
	public String getBrowesrName() {
		// TODO Auto-generated method stub 
		return ConfigurationActions.getBrowserToRun();
	}

    
   


}
