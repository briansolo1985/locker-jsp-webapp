package com.epam.tutorial.lockerapp.configuration;

import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * Factory class for creating proper Config instances.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class ConfigFactory {
	private ConfigFactory() {
	}

	/**
	 * Factory method instantiating chosen Config clas.
	 * 
	 * @param desired
	 *            class.
	 * @return Instantiated Reader class.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public static Config getInstance(Class<?> clazz) throws AppException {
		if (clazz.equals(LockerConfigReader.class)) {
			return new LockerConfig();
		} else if (clazz.equals(EmployeeConfigReader.class)) {
			return new EmployeeConfig();
		}

		AppException ae = new AppException();
		ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo("clazz",
				clazz, "unsupported class", "ConfigFactory"));
		throw ae;
	}

}
