package com.epam.tutorial.lockerapp.configuration;

/**
 * Class for reading Locker configuration from Property file.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerConfigReader extends ConfigReader {
	private final static String readerConfigPath = "lockers.properties";

	public LockerConfigReader() {
		super(readerConfigPath);
	}

}
