package com.epam.tutorial.lockerapp.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.entities.Employee;
import com.epam.tutorial.lockerapp.entities.EmployeeManager;
import com.epam.tutorial.lockerapp.entities.Locker;
import com.epam.tutorial.lockerapp.entities.LockerManager;
import com.epam.tutorial.lockerapp.exception.AppException;

/**
 * This is an interface class for communicating with client side through servlet
 * and jsp in a thread safe way.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerControl {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(LockerControl.class);

	private LockerManager lockerManager;
	private EmployeeManager employeeManager;

	public LockerControl() throws AppException {
		lockerManager = LockerManager.getInstance();
		employeeManager = EmployeeManager.getInstance();
		
		init();
	}

	/**
	 * Getting method for list of lockers.
	 * 
	 * @return List<Locker> or null.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public List<Locker> getLockers() {
		return lockerManager.getLockerList();
	}

	/**
	 * Getting method for list of employees.
	 * 
	 * @return List<Employee> or null.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public List<Employee> getUsers() {
		return employeeManager.getEmployeeList();
	}

	/**
	 * Initializing application environment for testing.
	 * 
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	
	public synchronized void init() throws AppException {
		employeeManager.load();
		lockerManager.load();
	}

	/**
	 * Interface method for reserving a locker for someone.
	 * 
	 * @param Employee
	 *            e, who wants to reserve.
	 * @param String
	 *            lockerID, target locker.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public synchronized void reserve(Employee employee, String lockerID)
			throws AppException {
		lockerManager.reserve(employee, lockerID);
	}

	/**
	 * Interface method for releasing a locker.
	 * 
	 * @param Employee
	 *            e, who wants to release.
	 * @param String
	 *            lockerID, target locker.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public synchronized void release(Employee employee, String lockerID)
			throws AppException {
		lockerManager.release(employee, lockerID);
	}

	/**
	 * Interface method for logging users in.
	 * 
	 * @param String
	 *            userID, string of three letters.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public synchronized Employee login(String userID) throws AppException {
		return employeeManager.login(userID);
	}

	/**
	 * Interface method for logging users out.
	 * 
	 * @param Employee
	 *            e, who wants to log out.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	public synchronized void logout(Employee employee) throws AppException {
		employeeManager.logout(employee);
	}

	public void setLockerManager(LockerManager lockerManager) {
		this.lockerManager = lockerManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

}
