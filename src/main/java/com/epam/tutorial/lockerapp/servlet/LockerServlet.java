package com.epam.tutorial.lockerapp.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.control.LockerControl;
import com.epam.tutorial.lockerapp.entities.Employee;
import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfo.ErrorType;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * Class for serving client requests, using business logic layer. Forwards
 * response to jsp - view layer
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(LockerServlet.class);

	/**
	 * Predefined server functions
	 */
	public enum ServletFunction {
		LOGIN, LOGOUT, RESERVE, RELEASE, CHANGE, INVALID;

		private static Map<String, ServletFunction> namedFunctions = new HashMap<>();
		static {
			namedFunctions.put("login", LOGIN);
			namedFunctions.put("logout", LOGOUT);
			namedFunctions.put("reserve", RESERVE);
			namedFunctions.put("release", RELEASE);
		}

		public static ServletFunction getServletFunction(String name)
				throws AppException {
			if (name == null) {
				return INVALID;
			}
			return (namedFunctions.get(name.trim().toLowerCase()) == null) ? INVALID
					: namedFunctions.get(name.trim().toLowerCase());
		}
	}

	private LockerControl lockerControl = null;

	public LockerServlet() {
	}

	/**
	 * Serving client requests, parsing parameters, calling proper functions,
	 * forwarding results to jsp view layer
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean redirected = false;

		try {
			lockerControl = new LockerControl();

			String functionName = request.getParameter("funcName");

			if (functionName != null) {
				String paramValue = request.getParameter("funcParam");

				ServletFunction srf = ServletFunction
						.getServletFunction(functionName);

				Employee e = (Employee) request.getSession().getAttribute(
						"loginUser");
				if (e == null && !srf.equals(ServletFunction.LOGIN)) {
					response.sendRedirect("index.jsp");
					redirected = true;
					return;
				}

				switch (srf) {
				case LOGIN:
					e = lockerControl.login(request.getParameter("funcParam"));
					logger.info("login succeeded for user={}", e);
					request.getSession().setAttribute("loginUser", e);
					break;
				case LOGOUT:
					lockerControl.logout(e);
					logger.info("logout succeeded for user={}", e);
					request.getSession().setAttribute("loginUser", null);
					break;
				case RELEASE:
					lockerControl.release(e, paramValue);
					break;
				case RESERVE:
					lockerControl.reserve(e, paramValue);
					break;
				case INVALID:
					AppException ae = new AppException();
					ae.addInfo(ErrorInfoFactory
							.getIllegalInputParameterErrorInfo("functionName",
									functionName,
									"name parameter must be not null",
									"ServletFunction")
							.setErrorType(ErrorType.CLIENT_ERROR)
							.setUserErrorDescription("Illegal function called"));
					throw ae;
				}
			}
		} catch (AppException ae) {
			logger.error("{}", ae);
			request.setAttribute(
					"error",
					("".equals(ae.getUserLog())) ? "Ooops... Something went wrong, please check error log!"
							: ae.getUserLog());
		} catch (Throwable t) {
			logger.error("{}", t);
			request.setAttribute("error",
					"Ooops... Something went wrong, please check error log!");
		} finally {
			if (!redirected) {
				String targetJSP = null;
				if (request.getAttribute("error") == null) {
					targetJSP = "/index.jsp";
				} else {
					targetJSP = "/error.jsp";
				}
				request.getRequestDispatcher(targetJSP).forward(request,
						response);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
