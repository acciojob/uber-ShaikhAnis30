package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int driverId;

    private String mobile;

    private String password;

    //Driver to Cab(OneToOne)
    //Parent(Driver)
    @OneToOne(mappedBy = "driver", cascade =  CascadeType.ALL)
    private Cab cab;


    //Driver to TripBookings --> How many trips driver taken
    //Parent(Driver)
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<TripBooking> tripsTakenByDriver = new ArrayList<>();


    public Driver() {
    }




    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripsTakenByDriver() {
        return tripsTakenByDriver;
    }

    public void setTripsTakenByDriver(List<TripBooking> tripsTakenByDriver) {
        this.tripsTakenByDriver = tripsTakenByDriver;
    }
}