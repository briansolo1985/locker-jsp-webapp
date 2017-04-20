package com.epam.tutorial.lockerapp.configuration;

import java.io.IOException;
import java.util.Properties;

import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * General reader class for reading Config item.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public abstract class ConfigReader {
	protected final String propertyFilePath;
	protected static final String propertyDirectory = "/com/epam/tutorial/lockerapp/resource/";

	public ConfigReader(String propertyFileName) {
		this.propertyFilePath = propertyDirectory + propertyFileName;
	}

	/**
	 * Default Config item reader from Property files
	 * 
	 * @param Nothing
	 *            .
	 * @return Config object read from Property file .
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public Config readConfig() throws AppException {
		Properties p = new Properties();

		try {
			p.load(this.getClass().getResourceAsStream(propertyFilePath));
		} catch (IOException ioe) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getResourceOperationErrorInfo(ioe,
					"property file open error", "ConfigReader").setParameter(
					"propertyFilePath", propertyFilePath));
			throw ae;
		}

		Config c = ConfigFactory.getInstance(this.getClass());
		c.process(p);

		return c;
	}

}
