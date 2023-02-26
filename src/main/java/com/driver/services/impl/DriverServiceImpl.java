package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.model.TripBooking;
import com.driver.model.TripStatus;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password) {
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Driver driver = new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);
		//forming cab and setting ratePerKm
		Cab cab = new Cab();
		cab.setPerKmRate(10);
		cab.setAvailable(true);
		driver.setCab(cab);

//		cabRepository3.save(cab);//saved parent --> Never save parent, question is asking for driver
		driverRepository3.save(driver);
	}

	@Override
	public void removeDriver(int driverId) {
		// Delete driver without using deleteById function
		Driver driver = driverRepository3.findById(driverId).get();
		//all the trips and cab of this driver should be canceled
//		List<TripBooking> allTripsOfThisDriver = driver.getTripsTakenByDriver();
//		for (TripBooking trip : allTripsOfThisDriver) {
//			if(trip.getStatus().equals(TripStatus.CONFIRMED)) {
//				trip.setStatus(TripStatus.CANCELED); //cancelled all the confirmed trips of this driver
//			}
//		}
//		//also delete the cab associated with this driver
//		Cab cab = driver.getCab();
//		cabRepository3.delete(cab); //Cab is parent of Driver

		driverRepository3.delete(driver); //by cascading effect tripsTakenByThisDriver will also be deleted
	}

	@Override
	public void updateStatus(int driverId) {
		//Set the status of respective car to unavailable
		Driver driver = driverRepository3.findById(driverId).get();
		driver.getCab().setAvailable(false);
		driverRepository3.save(driver);//saved parent //But save driver no need to save cab
	}
}
