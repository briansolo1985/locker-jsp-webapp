package com.epam.tutorial.lockerapp.configuration;

/**
 * Class representing a configuration item for a Locker.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerConfigItem extends ConfigItem {
	private String id;
	private String name;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Locker id string.
	 * @param name
	 *            Locker name string.
	 * @return instance.
	 */
	public LockerConfigItem(String id, String name) {
		this.id = id;
		this.name = name;
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
	 * Getter fo Name
	 * 
	 * @return Name string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for Name
	 * 
	 * @param id
	 *            Name string.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
