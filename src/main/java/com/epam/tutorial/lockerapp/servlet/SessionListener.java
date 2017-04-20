package com.epam.tutorial.lockerapp.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tutorial.lockerapp.entities.Employee;

/**
 * Sessions Listener class for removing user held resources when session ends
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class SessionListener implements HttpSessionListener {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(SessionListener.class);

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		Object o = session.getAttribute("loginUser");
		if (o != null) {
			Employee e = (Employee) o;
			e.releaseLocker();
			e.setLoggedIn(false);
		}
	}

}
