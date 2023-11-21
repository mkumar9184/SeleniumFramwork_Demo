package com.yash.selenium.webdriver.Configuration;


public class DriverInitializer {

    private String driverType;

    private DriverConfiguration configuration;

    public DriverInitializer(String browser)
    {
        this.driverType = browser;
    }

    public DriverConfiguration initialize()
    {

        if (null == configuration) {
            // String driverType = ConfigurationActions.getBrowserToRun();
            System.out.println("Browser --->" + driverType);
            
            String browser = driverType.replace("\"", "");
            if ("chrome".equalsIgnoreCase(browser)) {
                configuration = new ChromeDriverConfiguration();
            } else if ("firefox".equalsIgnoreCase(browser)) {
                configuration = new FirefoxDriverConfiguration();
            } else if ("ie".equalsIgnoreCase(browser)) {
                configuration = new IEDriverConfiguration();
            } else if ("html".equalsIgnoreCase(browser)) {
                // configuration = new HTMLUnitDriverConfiguration();
            } else {
                throw new IllegalArgumentException(driverType + " Driver type not supported");
            }
        }
        return configuration;
    }

}
