package com.design.car.rental.system.carrentalsystemapp.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    CUSTOMER(0,"Customer"),
    DRIVER(1,"Driver"),
    ADMIN(2,"Admin");


    private int userId;
    private String userType;

    UserTypeEnum(int id,String userType) {
        this.userId = id;
        this.userType = userType;
    }

    public static UserTypeEnum computeUserType(int userTypeId) {
        if(userTypeId == 0){
            return UserTypeEnum.CUSTOMER;
        }
        else if(userTypeId == 1){
            return UserTypeEnum.DRIVER;
        }
        else if(userTypeId == 2){
            return UserTypeEnum.ADMIN;
        }
        return null;
    }


}
