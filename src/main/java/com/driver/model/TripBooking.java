package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tripBooking")
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distanceInKm;

    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    private int bill;


    //TripBooking to Customer(ManyToOne)  --> How many Trips customer Took till now
    //Child(TripBooking)
    @ManyToOne
    @JoinColumn
    private Customer customer;


    //TripBooking to Driver   --> How many trips driver Took till now
    //Child(TripBooking)
    @ManyToOne
    @JoinColumn
    private Driver driver;

    public TripBooking() {
    }


    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}