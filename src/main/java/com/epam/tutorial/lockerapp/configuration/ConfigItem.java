package com.epam.tutorial.lockerapp.configuration;

import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * This is an abstract class for representing a general configuration item.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public abstract class ConfigItem {
	/**
	 * Generic casting method for getting the proper type from the generic list
	 * with type checking
	 * 
	 * @param clazz
	 *            T type class for casting this object to.
	 * @return T casted this.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public <T> T cast(Class<T> clazz) throws AppException {
		if (clazz.isInstance(this)) {
			return clazz.cast(this);
		}
		AppException ae = new AppException();
		ae.addInfo(ErrorInfoFactory.getClassCastErrorInfo("illegal cast",
				"ConfigItem", this.getClass(), clazz));
		throw ae;
	}
}
