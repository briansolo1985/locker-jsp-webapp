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
import com.epam.tutorial.lockerapp.entities.Locker;
import com.epam.tutorial.lockerapp.entities.LockerManager;
import com.epam.tutorial.lockerapp.exception.AppException;

public class LockerManagerTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(LockerManagerTest.class);

	/** Instance of the tested class */
	private LockerManager lockerManager;

	/** Mock class instances */
	private Map<String, Locker> lockerMap;

	/** Constants */

	private Locker getLocker() {
		Locker validlocker = new Locker();
		validlocker.setId("lock1");
		validlocker.setName("testLocker");
		validlocker.setOwner(null);
		validlocker.setReserved(false);

		return validlocker;
	}

	private Employee getEmployee() {
		Employee validEmployee = new Employee();
		validEmployee.setId("emp1");
		validEmployee.setName("testName");
		validEmployee.setLoggedIn(true);
		validEmployee.setLocker(null);

		return validEmployee;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		// instantiate unit under test
		lockerManager = LockerManager.getInstance();

		// instantiate mock instances
		lockerMap = EasyMock.createMock(TreeMap.class);

		// inject unit dependencies into test class as mock objects
		lockerManager.setLockerMap(lockerMap);
	}

	/**
	 * Test case: trying to reserve an unreserved locker for an appropriate user
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Reserve successful, service does not return with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testValidReservation() {
		// create the input for the tested service function:
		Locker validlocker;
		Employee validEmployee;

		validlocker = getLocker();
		validEmployee = getEmployee();

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(validlocker.getId())).andReturn(
				true);
		EasyMock.expect(lockerMap.get(validlocker.getId())).andReturn(
				validlocker);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.reserve(validEmployee, validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNull(
				"reserve must not throw an exception when it is a success", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}

	/**
	 * Test case: trying to reserve a locker for an appropriate user but the
	 * locker is somehow null
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Reserve unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidReservation_LockerNull() throws AppException {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.reserve(validEmployee, null);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"reserve must throw an exception when lockerID is null", ae);
	}

	/**
	 * Test case: trying to reserve a locker for an user but the user is somehow
	 * null
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Reserve unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidReservation_EmployeeNull() throws AppException {
		// create the input for the tested service function:
		Locker validlocker;

		validlocker = getLocker();

		// create the return-value of the mock service:

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.reserve(null, validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"reserve must throw an exception when employee is null", ae);
	}

	/**
	 * Test case: trying to reserve a locker for an appropriate user but the
	 * locker is the user's property already
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Reserve unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidReservation_ReserveOnMyLocker() throws AppException {
		// create the input for the tested service function:
		Locker validlocker;
		Employee validEmployee;

		validlocker = getLocker();
		validEmployee = getEmployee();

		validlocker.setReserve(true, validEmployee.getName());
		validEmployee.setLocker(validlocker);

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(validlocker.getId())).andReturn(
				true);
		EasyMock.expect(lockerMap.get(validlocker.getId())).andReturn(
				validlocker);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.reserve(validEmployee, validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"reserve must throw an exception when employee reserves again on his/her own locker",
				ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}

	/**
	 * Test case: trying to reserve a locker for an appropriate user but the
	 * locker is other user's property already
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Reserve unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidReservation_ReserveOnSomeOnesLocker()
			throws AppException {
		// create the input for the tested service function:
		Locker validlocker;
		Employee validEmployee;

		validlocker = getLocker();
		validEmployee = getEmployee();

		validlocker.setReserve(true, "Some One");

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(validlocker.getId())).andReturn(
				true);
		EasyMock.expect(lockerMap.get(validlocker.getId())).andReturn(
				validlocker);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.reserve(validEmployee, validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"reserve must throw an exception when employee reserves on someone's locker",
				ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}

	/**
	 * Test case: trying to release a locker of an appropriate user
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Release successful, service does not return with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testValidRelease() {
		// create the input for the tested service function:
		Locker validlocker;
		Employee validEmployee;

		validlocker = getLocker();
		validEmployee = getEmployee();

		validEmployee.setLocker(validlocker);
		validlocker.setReserve(true, validEmployee.getName());

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(validlocker.getId())).andReturn(
				true);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.release(validEmployee, validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNull(
				"release must not throw an exception when it is a success", ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}

	/**
	 * Test case: trying to release a locker of an appropriate user but input
	 * parameter lockerID is somehow null
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Release unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidRelease_LockerNull() {
		// create the input for the tested service function:
		Employee validEmployee;

		validEmployee = getEmployee();

		// create the return-value of the mock service:
		// call the tested function
		AppException ae = null;
		try {
			lockerManager.release(validEmployee, null);
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"release must throw an exception when locker is null", ae);
	}

	/**
	 * Test case: trying to release a locker of an user but input user object is
	 * somehow null
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Release unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidRelease_EmployeeNull() {
		// create the input for the tested service function:
		Employee validEmployee = null;
		Locker validLocker;
		validLocker = getLocker();

		// create the return-value of the mock service:
		// call the tested function
		AppException ae = null;
		try {
			lockerManager.release(validEmployee, validLocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"release must throw an exception when employee is null", ae);
	}

	/**
	 * Test case: trying to release a locker of an appropriate user but employee
	 * does not own any locker.
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Release unsuccessful, service does not return with an exception, but returns with null.</li>
	 * </ul>
	 */
	@Test
	public void testValidRelease_EmployeeNotOwnsAnyLocker() {
		// create the input for the tested service function:
		Locker validlocker;
		Employee validEmployee;

		validlocker = getLocker();
		validEmployee = getEmployee();

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(validlocker.getId())).andReturn(
				true);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			validlocker = lockerManager.release(validEmployee,
					validlocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNull(
				"release must not throw an exception when it is a success", ae);
		Assert.assertNull(
				"returning locker must be null when user not owns any locker",
				validlocker);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}

	/**
	 * Test case: trying to release a locker of an appropriate user but locker belongs to another user
	 * 
	 * Expected result:
	 * <ul>
	 * <li>Release unsuccessful, service returns with an exception.</li>
	 * </ul>
	 */
	@Test
	public void testInValidRelease_OtherEmployeesLocker() {
		// create the input for the tested service function:
		Locker otherLocker;
		Locker myLocker;
		Employee validEmployee;

		otherLocker = getLocker();
		myLocker = getLocker();
		validEmployee = getEmployee();

		validEmployee.setLocker(myLocker);
		myLocker.setReserve(true, validEmployee.getName());
		myLocker.setId("myLocker");

		otherLocker.setReserve(true, "Some One");
		otherLocker.setId("otherLocker");

		// create the return-value of the mock service:
		EasyMock.expect(lockerMap.containsKey(otherLocker.getId())).andReturn(
				true);
		EasyMock.replay(lockerMap);

		// call the tested function
		AppException ae = null;
		try {
			lockerManager.release(validEmployee, otherLocker.getId());
		} catch (AppException aex) {
			ae = aex;
		}

		Assert.assertNotNull(
				"release must throw an exception when trying to release someone's locker",
				ae);

		// ensure that the Mock instance is called as expected:
		EasyMock.verify(lockerMap);
	}
}
