package com.epam.tutorial.lockerapp.configuration;

import com.epam.tutorial.lockerapp.entities.Employee;
import com.epam.tutorial.lockerapp.entities.Locker;

/**
 * Factory class for getting proper Reader instance.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class ConfigReaderFactory {
	private ConfigReaderFactory() {
	}

	/**
	 * Factory method instantiating chosen Reader class.
	 * 
	 * @param desired
	 *            classs.
	 * @return Instantiated Config class.
	 */
	public static ConfigReader getInstance(Class<?> clazz)  {
		if (clazz.equals(Locker.class)) {
			return new LockerConfigReader();
		} else if (clazz.equals(Employee.class)) {
			return new EmployeeConfigReader();
		} else
			return null;
	}
}
