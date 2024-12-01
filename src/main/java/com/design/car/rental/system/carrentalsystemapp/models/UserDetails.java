package com.design.car.rental.system.carrentalsystemapp.models;

import com.design.car.rental.system.carrentalsystemapp.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class UserDetails extends BaseModel {

    private String name;
    private String phone;
    private String email;

    @OneToOne
    private Address address;
    private String licenseNo;

    @Enumerated(EnumType.ORDINAL)
    private UserTypeEnum userType;

    private Date cron;
    private Date updOn;

    @Column(name = "activeRecord")
    private int active;

}
