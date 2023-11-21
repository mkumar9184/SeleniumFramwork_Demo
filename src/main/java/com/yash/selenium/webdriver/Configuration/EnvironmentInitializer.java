package com.yash.selenium.webdriver.Configuration;

import com.yash.selenium.webdriver.Actions.ConfigurationActions;

public class EnvironmentInitializer {	
	
    public EnvironmentConfiguration initialize() {

        //String environmentToRunAgainst = System.getProperty("webEnv").toLowerCase();
    	String environmentToRunAgainst = ConfigurationActions.getEnvironmentToRun();
        System.out.println("==============RUNNING SUITE ON - " + environmentToRunAgainst + " =============================");
       
        EnvironmentConfiguration configurationToUse;

        switch (environmentToRunAgainst) {
            case "qa":
               configurationToUse = new QualEnvConfiguration();
                break;
  //          case "cert":
    //           configurationToUse = new MOEnvConfiguration();
     //           break;
            case "dev":
               configurationToUse = new DevEnvConfiguration();
                break;
            default:
              configurationToUse = new QualEnvConfiguration();
                break;
        }

        return configurationToUse;
    }

}
