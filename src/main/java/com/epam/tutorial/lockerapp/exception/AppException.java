package com.epam.tutorial.lockerapp.exception;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General exception class for handling errors and storing error items, making
 * error logs, etc.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class AppException extends Exception {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AppException.class);

	private static final long serialVersionUID = 1060583112626047093L;

	/*
	 * List for storing error items
	 */
	protected List<ErrorInfo> errorInfoList = new ArrayList<>();

	public AppException() {
	}

	/**
	 * Adds an error element to collection.
	 * 
	 * @param ErrorInfo
	 *            Use with ErrorItemFactory.
	 * @return The added ErrorInfo instance.
	 */
	public ErrorInfo addInfo(ErrorInfo info) {
		this.errorInfoList.add(info);
		return info;
	}

	/**
	 * Creates and adds an error element to collection.
	 * 
	 * @return The added ErrorInfo instance.
	 */
	public ErrorInfo addInfo() {
		ErrorInfo info = new ErrorInfo();
		this.errorInfoList.add(info);
		return info;
	}

	/**
	 * Creates error message from error items for end users
	 * 
	 * @return Created error message
	 */
	public String getUserLog() {
		StringBuilder sb = new StringBuilder();
		for (ErrorInfo ei : errorInfoList) {
			if (!"".equals(ei.getUserLogString())) {
				sb.append(String.format("%s%n", ei.getUserLogString()));
			}
		}
		return sb.toString();
	}

	/**
	 * Creates detailed error log from error items for developers
	 * 
	 * @return Created error message
	 */
	public String getDevLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("# DEVELOPMENT LOG");
		int i = 0;
		for (ErrorInfo ei : errorInfoList) {
			sb.append(String.format("%n  ErrorElement[%d]%n", i++));
			sb.append(String.format("%s%n", ei.getDevLogString()));
		}
		sb.append(String.format("# STACKTRACE"));
		return sb.toString();
	}

	@Override
	public String toString() {
		return getDevLog();
	}
}
