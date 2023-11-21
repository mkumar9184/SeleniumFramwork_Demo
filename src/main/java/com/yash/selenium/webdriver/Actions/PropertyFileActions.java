package com.yash.selenium.webdriver.Actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertyFileActions {

    public static void writeProperty(HashMap<String, String> propertyFileMapper, String propertyFilePath)
    {
        Properties prop = new Properties();
        OutputStream output = null;
        FileInputStream input = null;
        File file = null;

        try {
            file = new File(propertyFilePath);
            output = new FileOutputStream(file);
            input = new FileInputStream(file);
            prop.load(input);

            for (String property : propertyFileMapper.keySet()) {
                if (propertyFileMapper.get(property) == null) {
                    prop.put(property, "");
                } else {
                    prop.put(property, propertyFileMapper.get(property));
                }
            }
            prop.store(output, null);

            output.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static String getPropertyValue(String property, String propertyFilePath)
    {
        Properties prop = new Properties();
        File file = null;
        FileInputStream input = null;
        String result = null;
        try {
            file = new File(propertyFilePath);
            input = new FileInputStream(file);
            prop.load(input);
            input.close();
            result = prop.getProperty(property);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

  
    public static String deletePropertyValues(String property, String propertyFilePath)
    {
        File fileToBeModified = new File(propertyFilePath);
        String oldPropertyValues = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null) {
                oldPropertyValues = oldPropertyValues + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldPropertyValues.replaceAll(property, "");
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return property;
    }

    /**
     * @param propertyFilePath
     * @throws Throwable NT03928
     */
    public static void deletePrevivePropertiesValues(String propertyFilePath) throws Throwable
    {
        Properties prop = new Properties();
        OutputStream output = null;
        output = new FileOutputStream(propertyFilePath);
        prop.setProperty("TemplateActivity", "");
        prop.setProperty("Project", "");
        prop.setProperty("Resource", "");
        prop.setProperty("Activity", "");

        // save properties to project root folder
        prop.store(output, null);
    }
}
