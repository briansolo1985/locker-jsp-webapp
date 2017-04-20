package com.epam.tutorial.lockerapp.entities;

import com.epam.tutorial.lockerapp.exception.AppException;

/**
* Abstract class generalizing application entity manager classes.
* @author Ferenc Kis 
* @version 1.1
*/
public interface EntityManager {
	public void load() throws AppException;
}
