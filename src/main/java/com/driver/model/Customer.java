package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;

    private String mobile;

    private String password;

    //Customer to TripBookings --> (OneToMany)
    //Parent(Customer)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<TripBooking> listOfTripsBooked = new ArrayList<>();

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getListOfTripsBooked() {
        return listOfTripsBooked;
    }

    public void setListOfTripsBooked(List<TripBooking> listOfTripsBooked) {
        this.listOfTripsBooked = listOfTripsBooked;
    }
}