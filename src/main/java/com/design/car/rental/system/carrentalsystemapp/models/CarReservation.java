package com.design.car.rental.system.carrentalsystemapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class CarReservation extends BaseModel{

    @ManyToOne
    private CarDetails carDetails;
    private Date pickUpDate;
    private Date returnDate;
    private String pickUpLocation;
    private String returnLocation;
    @ManyToOne
    @JoinColumn(name = "customer_details_id")
    private UserDetails customerDetails;

    @ManyToOne
    @JoinColumn(name = "driver_details_id")
    private UserDetails driverDetails;
}
