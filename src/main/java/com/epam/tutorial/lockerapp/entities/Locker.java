package com.epam.tutorial.lockerapp.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.configuration.ConfigItem;
import com.epam.tutorial.lockerapp.configuration.ConfigReader;
import com.epam.tutorial.lockerapp.configuration.LockerConfigItem;
import com.epam.tutorial.lockerapp.exception.AppException;

/**
 * Class representing a Locker entity.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class Locker implements Comparable<Locker> {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigReader.class);

	/* Private fields */
	private String id;
	private String name;
	private boolean reserved;
	private String owner;
	
	/**
	 * Public constructor for testing
	 */
	public Locker() {
	}

	/**
	 * Private constructor
	 * 
	 * @param ConfigItem
	 *            ci containing necessary data.
	 * @return Locker instance.
	 */
	private Locker(ConfigItem ci) throws AppException {
		LockerConfigItem lci = ci.cast(LockerConfigItem.class);

		this.id = lci.getId();
		this.name = lci.getName();
		this.setReserved(false);
		this.setOwner("");
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

	/* Overrided methods for logging, and hash container use */
	@Override
	public String toString() {
		return String.format("[id=%s name=%s]", id, name);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Locker) {
			Locker e = (Locker) o;
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
	 * Static factory method for creating Locker inctances.
	 * 
	 * @param ConfigItem
	 *            ci containing necessary data.
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	protected static Locker createFromConfigItem(ConfigItem ci)
			throws AppException {
		return new Locker(ci);
	}

	/**
	 * Getter for reserved property
	 * 
	 * @return reserved property as boolean.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * Setter for reserved property
	 * 
	 * @param reserved
	 *            boolean.
	 */
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	/**
	 * Getter for owner property
	 * 
	 * @return owner property as string.
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Setter for owner property
	 * 
	 * @param owner
	 *            String.
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int compareTo(Locker locker) {
		if(this.id.matches("-?\\d+") && locker.id.matches("-?\\d+") ) {
			int thisID = Integer.parseInt(this.id);
			int thatID = Integer.parseInt(locker.id);
			
			if( thisID < thatID ) {
				return -1;
			} else if( thisID > thatID ) {
				return 1;
			} else {
				return 0;
			}
		}
		
		return 1;
	}
	
	public void setReserve(boolean reserved, String ownerName) {
		this.setReserved(reserved);
		this.setOwner(ownerName);
	}
}