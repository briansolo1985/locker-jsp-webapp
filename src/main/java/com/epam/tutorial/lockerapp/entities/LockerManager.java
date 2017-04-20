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
import com.epam.tutorial.lockerapp.exception.ErrorInfo.ErrorType;
import com.epam.tutorial.lockerapp.exception.ErrorInfoFactory;

/**
 * Class representing a locker room, storing and managing lockers. Acts like a
 * locker room. Singleton.
 * 
 * @author Ferenc Kis
 * @version 1.1
 */
public class LockerManager implements EntityManager {
	private static final Logger logger = LoggerFactory
			.getLogger(LockerManager.class);

	private Map<String, Locker> lockerMap = new TreeMap<>();
	private final String name;
	
	private ConfigReader configReader = null;

	/* Thread safe singleton implementation */
	private LockerManager() {
		name = LockerManager.class.getName();
		configReader = ConfigReaderFactory.getInstance(Locker.class);
		
	}

	private static class LockerManagerHolder {
		private static LockerManager lockerManager = new LockerManager();
	}

	public static LockerManager getInstance() {
		return LockerManagerHolder.lockerManager;
	}

	/**
	 * Getting method for list of lockers.
	 * 
	 * @return unmodifiable List<Locker>.
	 */
	public List<Locker> getLockerList() {
		List<Locker> l = new ArrayList<>();
		l.addAll(lockerMap.values());
		logger.debug("presort {}", l);
		Collections.sort(l);
		logger.debug("postsort {}", l);
		return Collections.unmodifiableList(l);
	}

	/* method overriding for hashmap usage */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof LockerManager) {
			LockerManager l = (LockerManager) o;
			if (this.name.equals(l.name)) {
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
	 * Loads the stored configuration.
	 * 
	 * @exception AppException
	 *                on error.
	 * @see AppException
	 */
	@Override
	public void load() throws AppException {
		if (LockerManagerHolder.lockerManager.lockerMap.isEmpty()) {
			logger.debug("lockermanager is loading");

			Config c = configReader.readConfig();

			for (ConfigItem ci : c.getConfigItemList()) {
				Locker l = Locker.createFromConfigItem(ci);
				lockerMap.put(l.getId(), l);
			}
		}
	}

	/**
	 * Checks the input parameters for null and availability.
	 * 
	 * @param Employee
	 *            employee target employee object
	 * @param String
	 *            lockerID target locker's id
	 * @exception AppException
	 *                on validation error.
	 * @see AppException
	 */
	private void validateParameters(Employee employee, String lockerID)
			throws AppException {
		if (employee == null) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory
					.getIllegalInputParameterErrorInfo("employee", employee,
							"employee parameter must be not null",
							"LockerManager")
					.setErrorType(ErrorType.CLIENT_ERROR)
					.setUserErrorDescription("User is not logged in"));
			throw ae;
		}
		if (lockerID == null || !lockerMap.containsKey(lockerID)) {
			AppException ae = new AppException();
			ae.addInfo(ErrorInfoFactory
					.getIllegalInputParameterErrorInfo("lockerID", lockerID,
							"lockerID parameter must be not null",
							"LockerManager")
					.setErrorType(ErrorType.CLIENT_ERROR)
					.setUserErrorDescription("Illegal Locker ID"));
			throw ae;
		}
	}

	/**
	 * Checks for reserve availability for argumet employee and locker, and
	 * performs reserve process.
	 * 
	 * @param Employee
	 *            employee target employee object
	 * @param String
	 *            lockerID target locker's id
	 * @exception AppException
	 *                on validation error.
	 * @see AppException
	 */
	public Locker reserve(Employee employee, String lockerID) throws AppException {
		validateParameters(employee, lockerID);

		Locker targetLocker = lockerMap.get(lockerID);
		Locker myLocker = employee.getLocker();

		if (targetLocker.isReserved()) {
			if ((myLocker != null) && (lockerID.equals(myLocker.getId()))) {
				// my locker cannot be rereserved
				AppException ae = new AppException();
				ae.addInfo(ErrorInfoFactory
						.getUnsupportedOperationErrorInfo(
								"illegal locker reserve operation - reserve on my locker",
								"LockerManager")
						.setErrorType(ErrorType.CLIENT_ERROR)
						.setUserErrorDescription(
								"This locker is already yours!"));
				throw ae;
			} else {
				// someones locker
				AppException ae = new AppException();
				ae.addInfo(ErrorInfoFactory
						.getUnsupportedOperationErrorInfo(
								"illegal locker reserve operation - reserve on someone locker",
								"LockerManager")
						.setErrorType(ErrorType.CLIENT_ERROR)
						.setUserErrorDescription(
								"Locker already belongs to someone. Leave it alone."));
				throw ae;
			}
		}
		
		employee.releaseLocker();
		employee.reserveLocker(targetLocker);
		
		logger.info("user={} reserved locker={} from locker={}", employee,
				targetLocker, myLocker);
		
		return targetLocker;
	}

	/**
	 * Checks for release availability for argumet employee and locker, and
	 * performs release process.
	 * 
	 * @param Employee
	 *            employee target employee object
	 * @param String
	 *            lockerID target locker's id
	 * @exception AppException
	 *                on validation error.
	 * @see AppException
	 */
	public Locker release(Employee employee, String lockerID) throws AppException {
		validateParameters(employee, lockerID);

		Locker myLocker = employee.getLocker();

		if (myLocker != null) {
			if ( !lockerID.equals(myLocker.getId())) {
				AppException ae = new AppException();
				ae.addInfo(ErrorInfoFactory
						.getUnsupportedOperationErrorInfo(
								"illegal locker release operation", "LockerManager")
						.setErrorType(ErrorType.CLIENT_ERROR)
						.setUserErrorDescription(
								"Locker does not belongs to you. Leave it alone."));
				throw ae;
			}
			
			employee.releaseLocker();
			logger.info("user={} released locker={}", employee, myLocker);
		}
		
		return myLocker;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}
	
	public Locker addLocker(Locker l) {
		return lockerMap.put(l.getId(), l);
	}

	public void setLockerMap(Map<String, Locker> lockerMap) {
		this.lockerMap = lockerMap;
	}

}
