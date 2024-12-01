package com.design.car.rental.system.carrentalsystemapp.models;

import com.design.car.rental.system.carrentalsystemapp.enums.CarCategoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class CarDetails extends BaseModel {

    private String name;
    private String brand;
    private String vechicleNo;

    @Enumerated(EnumType.ORDINAL)
    private CarCategoryEnum vechicleCategory;
    private String manufacturingYear;
    private int mileage;
    private boolean hasSunRoof;
}
