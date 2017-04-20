package com.epam.tutorial.lockerapp.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * This is an abstract class for holding general configuration items.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public abstract class Config {
	protected List<ConfigItem> configItemList = new ArrayList<>();

	/**
	 * This abstract method is generalizing process methods which making
	 * ConfigItems from Properties object and store them in ConfigItemList
	 * 
	 * @param p
	 *            The Properties object to processed.
	 * @return Nothing.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public abstract void process(Properties p) throws AppException;

	public List<ConfigItem> getConfigItemList() {
		return configItemList;
	}

	/**
	 * This method is for validating property items to ensure they have the same
	 * cardinality. (e.g. the structure is not corrupted)
	 * 
	 * @param p
	 *            Property items as objects (string).
	 * @return Boolean result of the validation.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	protected boolean validate(Object... arg) throws AppException {
		Set<Integer> s = new HashSet<>();

		if (arg.length < 2) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
					"arg", arg, "at least 2 or more elements must be compared",
					"ConfigReader"));
			throw ae;
		}
		for (Object o : arg) {
			if (o == null) {
				AppException ae = new AppException();
				ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
						"o", o, "o value is null", "ConfigReader"));
				throw ae;
			}
		}

		int i = 0;
		for (Object o : arg) {
			if (o instanceof String) {
				s.add(((String) o).split(",").length);
			} else {
				AppException ae = new AppException();
				ae.addInfo(ErrorInfoFactory.getIllegalInputParameterErrorInfo(
						"o[" + i + "]", o, "object is not instance of string",
						"ConfigReader"));
				throw ae;
			}
			i++;
		}
		return (s.size() == 1) ? true : false;
	}

	public void setConfigItemList(List<ConfigItem> configItemList) {
		this.configItemList = configItemList;
	}
}
