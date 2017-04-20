package com.epam.tutorial.lockerapp.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.configuration.Config;
import com.epam.tutorial.lockerapp.configuration.ConfigItem;
import com.epam.tutorial.lockerapp.configuration.ConfigReader;
import com.epam.tutorial.lockerapp.configuration.ConfigReaderFactory;
import com.epam.tutorial.lockerapp.exception.AppException;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * Class representing an employee catalog, storing and managing employess.
 * Singleton.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class EmployeeManager implements EntityManager {
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigReader.class);

	private Map<String, Employee> employeeMap = new TreeMap<>();
	private final String name;

	/* Thread safe singleton implementation */
	private EmployeeManager() {
		name = EmployeeManager.class.getName();
	}

	private static class EmployeeManagerHolder {
		private static EmployeeManager employeeManager = new EmployeeManager();
	}

	public static EmployeeManager getInstance() {
		return EmployeeManagerHolder.employeeManager;
	}

	/**
	 * Getting method for list of employees.
	 * 
	 * @return unmodifiable List<Employee>.
	 */
	public List<Employee> getEmployeeList() {
		List<Employee> l = new ArrayList<>();
		l.addAll(employeeMap.values());
		return Collections.unmodifiableList(l);
	}

	/**
	 * Loads the stored configuration.
	 * 
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	@Override
	public void load() throws AppException {
		if (EmployeeManagerHolder.employeeManager.employeeMap.isEmpty()) {
			logger.debug("employeeManager is loading");

			Config c = ConfigReaderFactory.getInstance(Employee.class)
					.readConfig();

			for (ConfigItem ci : c.getConfigItemList()) {
				Employee e = Employee.createFromConfigItem(ci);
				employeeMap.put(e.getId(), e);
			}
		}
	}

	/* method overriding for hashmap usage */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof LockerManager) {
			EmployeeManager e = (EmployeeManager) o;
			if (this.name.equals(e.name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Checks user creditential and performs login.
	 * 
	 * @param String
	 *            id of employee
	 * @exception AppException
	 *                on validation error.
	 * @see AppException
	 */
	public Employee login(String userID) throws AppException {
		if (!employeeMap.containsKey(userID)) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getSessionOperationErrorInfo(
					"authentication failed", "EmployeeManager").setParameter(
					"userID", userID));
			throw ae;
		}

		Employee e = employeeMap.get(userID);

		if (e.isLoggedIn()) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory
					.getSessionOperationErrorInfo("already logged in",
							"EmployeeManager").setParameter("userID", userID)
					.setUserErrorDescription("Already logged in!"));
			throw ae;
		}
		e.setLoggedIn(true);

		return e;
	}

	/**
	 * Checks user creditentials and performs logout. Frees held resources.
	 * 
	 * @param Employee
	 *            employee object whose owner wants to logout
	 * @exception AppException
	 *                on validation error.
	 * @see AppException
	 */
	public void logout(Employee employee) throws AppException {
		if (!employeeMap.containsValue(employee)) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getSessionOperationErrorInfo(
					"logout failed", "EmployeeManager").setParameter(
					"employee", employee));
			throw ae;
		}
		if (!employee.isLoggedIn()) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory.getSessionOperationErrorInfo(
					"not logged in", "EmployeeManager").setParameter(
					"employee", employee));
			throw ae;
		}

		employee.releaseLocker();
		employee.setLoggedIn(false);
	}

	public void setEmployeeMap(Map<String, Employee> employeeMap) {
		this.employeeMap = employeeMap;
	}
}
