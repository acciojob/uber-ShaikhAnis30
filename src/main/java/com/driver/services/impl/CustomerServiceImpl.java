package com.driver.services.impl;


import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;
import java.util.TreeSet;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> allDrivers =  driverRepository2.findAll();
		TreeSet<Integer> driversById = new TreeSet<>();
		for (Driver driver : allDrivers) {
			int id = driver.getDriverId();
			driversById.add(id);
		}
		Customer customer = customerRepository2.findById(customerId).get();
		TripBooking tripBooking = new TripBooking();
		boolean flag = false;
		for (int id : driversById) {
			Driver driver = driverRepository2.findById(id).get();
			if(driver.getCab().getAvailable()) {
				flag = true;

				//setting attributes
				tripBooking.setFromLocation(fromLocation);
				tripBooking.setToLocation(toLocation);
				tripBooking.setDistanceInKm(distanceInKm);
				tripBooking.setStatus(TripStatus.CONFIRMED);
				tripBooking.setBill(distanceInKm * 10);  //1Km - 10rs
				//now foreign key attribute
				tripBooking.setCustomer(customer);
				tripBooking.setDriver(driver);

				//between driver and TripBooking
				List<TripBooking> tripsByDriver = driver.getTripsTakenByDriver();
				tripsByDriver.add(tripBooking);
				driver.setTripsTakenByDriver(tripsByDriver);
				driver.getCab().setAvailable(false); //no need I think

				//between customer and TripBooking
				List<TripBooking> tripsByCustomer = customer.getListOfTripsBooked();
				tripsByCustomer.add(tripBooking);
				customer.setListOfTripsBooked(tripsByCustomer);

//				driverRepository2.save(driver);
				break;
			}
		}

		if(!flag) throw new Exception("No cab available!");
		customerRepository2.save(customer);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		//setting attributes
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);

		//between Customer and TripBooking
		Customer customer = tripBooking.getCustomer();
		List<TripBooking> tripsBookedByCustomer = customer.getListOfTripsBooked();
		tripsBookedByCustomer.remove(tripBooking);
		customer.setListOfTripsBooked(tripsBookedByCustomer);

		//between Driver and TripBooking
		Driver driver = tripBooking.getDriver();
		List<TripBooking> tripsBookedByDriver = driver.getTripsTakenByDriver();
		tripsBookedByDriver.remove(tripBooking);
		driver.setTripsTakenByDriver(tripsBookedByDriver);
		driver.getCab().setAvailable(true); //cab will also be available now

		customerRepository2.save(customer);//save parent
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		//setting attributes
		tripBooking.setStatus(TripStatus.COMPLETED);
//		tripBooking.setBill(0);
		Driver driver = tripBooking.getDriver();
		driver.getCab().setAvailable(true);//journey completed, so cab is available for upcoming customer
		driverRepository2.save(driver);
	}
}
