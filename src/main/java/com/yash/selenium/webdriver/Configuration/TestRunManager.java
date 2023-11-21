package com.yash.selenium.webdriver.Configuration;


public class TestRunManager 
{
    /**
     * set default system values in case there was not JVM args
     */
    static {

        System.setProperty("webEnv", "qa");
        System.setProperty("runtime.environment", "ci");
    }

    private static EnvironmentConfiguration envConfiguration;

    private static DriverConfiguration driverConfiguration;

    public static DriverConfiguration getDriverConfiguration(String browser)
    {
        if (null == driverConfiguration) {
            driverConfiguration = new DriverInitializer(browser).initialize();
        }
        return driverConfiguration;
    }

    public static EnvironmentConfiguration getConfiguration()
    {
        if (null == envConfiguration) {
            envConfiguration = new EnvironmentInitializer().initialize();
        }
        return envConfiguration;
    }

}
