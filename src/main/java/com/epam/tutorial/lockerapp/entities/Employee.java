package com.epam.tutorial.lockerapp.entities;

import com.epam.tutorial.lockerapp.configuration.ConfigItem;
import com.epam.tutorial.lockerapp.configuration.EmployeeConfigItem;
import com.epam.tutorial.lockerapp.exception.AppException;

/**
 * Class representing an Employee entity.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class Employee {
	/* Private fields */
	private String id;
	private String name;
	private boolean loggedIn;
	private Locker locker;

	/**
	 * Public constructor for testing
	 */
	public Employee() {
	}

	/**
	 * Private constructor
	 * 
	 * @param ConfigItem
	 *            ci containing necessary data.
	 * @return Locker instance.
	 */
	private Employee(ConfigItem ci) throws AppException {
		EmployeeConfigItem lci = ci.cast(EmployeeConfigItem.class);

		this.id = lci.getId();
		this.name = lci.getName();
		this.loggedIn = false;
	}

	/**
	 * Getter for ID
	 * 
	 * @return ID string.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for ID
	 * 
	 * @param id
	 *            ID string.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for Name
	 * 
	 * @return Name string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for Name
	 * 
	 * @param name
	 *            Name string.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for loggedIn property
	 * 
	 * @return loggedIn property as boolean.
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Setter for loggedIn property
	 * 
	 * @param loggedIn
	 *            property as boolean.
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/* Overrided method for logging, and hash container use */
	@Override
	public String toString() {
		return String.format("[id=%s name=%s]", id, name);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Employee) {
			Employee e = (Employee) o;
			if (this.id.equals(e.getId()) && this.name.equals(e.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 7 * (id == null ? 0 : id.hashCode()) + 53
				* (name == null ? 0 : name.hashCode());
	}

	/**
	 * Static factory method for creating Employee instances.
	 * 
	 * @param Employee
	 *            ci containing necessary data.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	protected static Employee createFromConfigItem(ConfigItem ci)
			throws AppException {
		return new Employee(ci);
	}

	/**
	 * Getter for locker property (locker owned by employee)
	 * 
	 * @return Locker property.
	 */
	public Locker getLocker() {
		return locker;
	}

	/**
	 * Setter for locker property (locker owned by employee)
	 * 
	 * param Locker property.
	 */
	public void setLocker(Locker locker) {
		this.locker = locker;
	}
	
	public boolean releaseLocker() {
		if( locker != null ) {
			locker.setReserve(false, "");
			this.setLocker(null);
			return true;
		}
		return false;	
	}
	
	public boolean reserveLocker(Locker locker) {
		if( locker != null ) {
			locker.setReserve(true, this.getName());
			this.setLocker(locker);
			return false;
		}
		return false;	
	}
}
