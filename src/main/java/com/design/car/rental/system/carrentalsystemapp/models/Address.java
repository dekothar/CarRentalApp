package com.design.car.rental.system.carrentalsystemapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Address extends BaseModel {

    private String street;
    private String city;
    private String state;
    private String zip;

}
