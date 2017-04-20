package com.epam.tutorial.lockerapp.exception;

import java.io.IOException;

import com.epam.tutorial.lockerapp.exception.ErrorInfo.ErrorType;
import com.epam.tutorial.lockerapp.exception.ErrorInfo.Severity;

/**
 * Factory for predefined erroritems
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class ErrorInfoFactory {
	public static final ErrorInfo getIllegalInputParameterErrorInfo(
			String parameterName, Object parameterValue,
			String errorDescription, String context) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("IllegalInputParameterError");
		info.setContextId(context);

		info.setErrorType(ErrorType.CLIENT_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);

		info.setParameter(parameterName, parameterValue);

		return info;
	}

	public static final ErrorInfo getResourceOperationErrorInfo(
			IOException ioe, String errorDescription, String context) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("ResourceOperationError");
		info.setContextId(context);

		info.setErrorType(ErrorType.SERVICE_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);
		info.setCause(ioe);

		return info;
	}

	public static final ErrorInfo getUnsupportedOperationErrorInfo(
			String errorDescription, String context) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("UnsupportedOperationError");
		info.setContextId(context);

		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);

		return info;
	}

	public static final ErrorInfo getIllegalConversionErrorInfo(
			String errorDescription, String context, String pname1,
			Object pval1, String pname2, Object pval2) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("IllegalConversionError");
		info.setContextId(context);

		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);

		info.setParameter(pname1, pval1);
		info.setParameter(pname2, pval2);

		return info;
	}

	public static final ErrorInfo getValidationErrorInfo(
			String errorDescription, String context) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("ValidationError");
		info.setContextId(context);

		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);

		return info;
	}

	public static final ErrorInfo getClassCastErrorInfo(
			String errorDescription, String context, Class<?> sourceClass,
			Class<?> targetClass) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("ClassCastError");
		info.setContextId(context);

		info.setErrorType(ErrorType.INTERNAL_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setParameter("sourceClass", sourceClass);
		info.setParameter("targetClass", targetClass);

		info.setErrorDescription(errorDescription);

		return info;
	}

	public static final ErrorInfo getSessionOperationErrorInfo(
			String errorDescription, String context) {

		ErrorInfo info = new ErrorInfo();

		info.setErrorId("SessionOperationError");
		info.setContextId(context);

		info.setErrorType(ErrorType.CLIENT_ERROR);
		info.setSeverity(Severity.ERROR);

		info.setErrorDescription(errorDescription);
		info.setUserErrorDescription("Invalid username");

		return info;
	}
}
