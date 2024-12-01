package com.design.car.rental.system.carrentalsystemapp.repository;

import com.design.car.rental.system.carrentalsystemapp.enums.UserTypeEnum;
import com.design.car.rental.system.carrentalsystemapp.models.UserDetails;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long> {

    Optional<UserDetails> findByIdAndActive(long id,int active);
    List<UserDetails> findByUserTypeAndActive(UserTypeEnum userType,int active);
    List<UserDetails> findAllByActive(int active);
}
