package com.design.car.rental.system.carrentalsystemapp.repository;

import com.design.car.rental.system.carrentalsystemapp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findById(long id);

    Address save(Address address);
}
