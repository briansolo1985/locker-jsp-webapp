package com.epam.tutorial.lockerapp.configuration;

/**
 * Class for reading Employee configuration from Property file.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class EmployeeConfigReader extends ConfigReader {
	private final static String readerConfigPath = "employees.properties";

	public EmployeeConfigReader() {
		super(readerConfigPath);
	}
}
