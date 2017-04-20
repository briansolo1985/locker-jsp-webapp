package com.epam.tutorial.lockerapp.test;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.entities.Employee;
import com.epam.tutorial.lockerapp.entities.EmployeeManager;
import com.epam.tutorial.lockerapp.exception.AppException;

public class EmployeeManagerTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(LockerManagerTest.class);

	/** Instance of the tested class */
	private EmployeeManager employeeManager;

	/** Mock class instances */
	private Map<String, Employee> employeeMap;

	/** Constants */

	private Employee getEmployee() {
		Employee validEmployee = new Employee();
		validEmployee.setId("emp1");
		validEmployee.setName("testName");
		validEmployee.setLoggedIn(false);
		validEmployee.setLocker(null);

		return validEmployee;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		// instantiate unit under test
		employeeManager = EmployeeManager.getInstance();

		// instantiate mock instances
		employeeMap = EasyMock.createMock(TreeMap.class);

		// inject unit dependencies into test class as mock objects
		employeeManager.setEmployeeMap(employeeMap);
	}

	/**
	 * Test case: trying to log in user with valid id
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Login successful, service does not return with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testValidLogin() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsKey(validEmployee.getId()))
				.andReturn(true);
		EasyMock.expect(employeeMap.get(validEmployee.getId())).andReturn(
				validEmployee);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.login(validEmployee.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNull(
				"login must not throw an exception when it is a success", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}

	/**
	 * Test case: trying to log in user with invalid id
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Login unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidLogin_InvalidUserId() {
		// create the input for the tested service function:
		final String invalidUserId = "invalid";

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsKey(invalidUserId))
				.andReturn(false);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.login(invalidUserId);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"login must throw an exception when user id not valid", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}

	/**
	 * Test case: trying to log in user who is still logged in
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Login unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidLogin_LoginAgainSameUser() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();
		validEmployee.setLoggedIn(true);

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsKey(validEmployee.getId()))
				.andReturn(true);
		EasyMock.expect(employeeMap.get(validEmployee.getId())).andReturn(
				validEmployee);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.login(validEmployee.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"login must throw an exception when user relogs on valid session",
				ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}

	/**
	 * Test case: trying to log out user
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Logout successful, service does not return with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testValidLogout() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();
		validEmployee.setLoggedIn(true);

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsValue(validEmployee)).andReturn(
				true);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.logout(validEmployee);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNull(
				"logout must not throw an exception when it is a success", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}

	/**
	 * Test case: trying to log out user with invalid userId
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Logout unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidLogout_InvalidUserId() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsValue(validEmployee)).andReturn(
				true);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.logout(validEmployee);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"logout must throw an exception when user id not valid", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}

	/**
	 * Test case: trying to log out user who is not logged in
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Logout unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidLogout_UserNotLoggedIn() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();
		validEmployee.setLoggedIn(false);

		// create the return-value of the mock service:
		EasyMock.expect(employeeMap.containsValue(validEmployee)).andReturn(
				true);
		EasyMock.replay(employeeMap);

		// call the tested function
		AppException ae = null;
		try {
			employeeManager.logout(validEmployee);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"logout must throw an exception when user is not logged in", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(employeeMap);
	}
}
