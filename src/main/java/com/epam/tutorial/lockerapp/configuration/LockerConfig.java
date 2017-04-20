package com.epam.tutorial.lockerapp.configuration;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * This is an exact class for processing Locker configuration items.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerConfig extends Config {
	private static final Logger logger = LoggerFactory
			.getLogger(LockerConfig.class);

	/**
	 * This method is processing the Locker objects' ConfigItems from Properties
	 * object and store them in ConfigItemList. Same as EmployeeConfig, but very
	 * possible to change in the future.
	 * 
	 * @param p
	 *            The Properties object to processed.
	 * @return Nothing.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	@Override
	public void process(Properties p) throws AppException {
		Object o1 = p.get("id");
		Object o2 = p.get("name");

		if (!validate(o1, o2)) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getValidationErrorInfo(
					"property structure is corrupted", "Config").setParameter(
					"p", p));
			throw ae;
		}

		String[] ida = ((String) o1).split(",");
		String[] namea = ((String) o2).split(",");

		for (int i = 0; i < ida.length; i++) {
			String id = ida[i].trim();
			String name = namea[i].trim();
			if (id.equals("") || name.equals("")) {
				logger.warn("skipping empty property [id={} name={}]", id, name);
			} else {
				configItemList.add(new LockerConfigItem(id, name));
			}
		}
	}

}
