package com.design.car.rental.system.carrentalsystemapp.dtos;

import com.design.car.rental.system.carrentalsystemapp.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private String name;
    private String phone;
    private String email;
    private Address address;
    private String licenseNo;
    private long userId;
}
