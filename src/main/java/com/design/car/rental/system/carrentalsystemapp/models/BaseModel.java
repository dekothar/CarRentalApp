package com.design.car.rental.system.carrentalsystemapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;

    @Transient
    private int active;


}
