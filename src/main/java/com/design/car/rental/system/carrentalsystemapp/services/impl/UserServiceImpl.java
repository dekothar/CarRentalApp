package com.design.car.rental.system.carrentalsystemapp.services.impl;

import com.design.car.rental.system.carrentalsystemapp.enums.UserTypeEnum;
import com.design.car.rental.system.carrentalsystemapp.exceptions.UserNotFoundException;
import com.design.car.rental.system.carrentalsystemapp.models.Address;
import com.design.car.rental.system.carrentalsystemapp.models.UserDetails;
import com.design.car.rental.system.carrentalsystemapp.repository.AddressRepository;
import com.design.car.rental.system.carrentalsystemapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static final int ACTIVE = 1;

    private static final int IN_ACTIVE = 0;

    private static final String USER_DOES_NOT_EXIST_WITH_THIS_ID = "User doesn't exist with this id";

    /**
     * Adds a new user to the system with the given details.
     * - Computes user details based on the input parameters.
     * - Saves the address to the database if provided.
     * - Sets the user as active and saves the user details in the database.
     *
     * @param name       The name of the user.
     * @param phone      The phone number of the user.
     * @param email      The email address of the user.
     * @param address    The address of the user (can be null).
     * @param licenseNo  The license number of the user.
     * @param userTypeId The ID of the user type.
     * @return The saved UserDetails object.
     */
    @Override
    public UserDetails addUser(String name, String phone, String email, Address address, String licenseNo, int userTypeId) {
        UserDetails userDetails = prepareUserDetails(name, phone, email, address, licenseNo, userTypeId, ACTIVE);
        if (address != null) {
            addressRepository.save(address);
        }
        return userRepository.save(userDetails);

    }

    /**
     * Retrieves a list of active user details based on the user type.
     *
     * @param userTypeId The ID of the user type to filter by.
     * @return A list of active UserDetails objects for the specified user type.
     */
    @Override
    public List<UserDetails> getUserDetailsByUserType(int userTypeId) {
        return userRepository.findByUserTypeAndActive(UserTypeEnum.computeUserType(userTypeId), ACTIVE);

    }

    /**
     * Retrieves user details by user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserDetails object corresponding to the user ID.
     * @throws UserNotFoundException If no active user exists with the given ID.
     */
    @Override
    public UserDetails getUserDetailsByUserId(long userId) throws UserNotFoundException {
        return getUserDetails(userId);
    }

    /**
     * Private helper method to retrieve user details by user ID, ensuring the user is active.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserDetails object corresponding to the user ID.
     * @throws UserNotFoundException If no active user exists with the given ID.
     */
    private UserDetails getUserDetails(long userId) throws UserNotFoundException {
        return userRepository.findByIdAndActive(userId, ACTIVE).orElseThrow(() -> new UserNotFoundException(USER_DOES_NOT_EXIST_WITH_THIS_ID));
    }

    /**
     * Retrieves all active user details from the system.
     *
     * @return A list of active UserDetails objects.
     */
    @Override
    public List<UserDetails> getAllActiveUserDetails() {
        return userRepository.findAllByActive(ACTIVE);
    }

    /**
     * Updates user details for a given user ID with the new provided details.
     * - Ensures the user exists and is active before updating.
     * - Retains the active status and user ID during the update.
     *
     * @param id         The ID of the user to update.
     * @param name       The updated name.
     * @param phone      The updated phone number.
     * @param email      The updated email address.
     * @param address    The updated address (can be null).
     * @param licenseNo  The updated license number.
     * @param userTypeId The updated user type ID.
     * @return The updated UserDetails object.
     * @throws UserNotFoundException If no active user exists with the given ID.
     */
    @Override
    public UserDetails updateUserDetails(long id, String name, String phone, String email, Address address, String licenseNo, int userTypeId) throws UserNotFoundException {
        UserDetails userDetails = getUserDetails(id);
        if (userDetails.getId() != id) {
            throw new UserNotFoundException(USER_DOES_NOT_EXIST_WITH_THIS_ID);
        }
        UserDetails updatedDetails = prepareUserDetails(name, phone, email, address, licenseNo, userTypeId, ACTIVE);
        updatedDetails.setId(userDetails.getId());
        return userRepository.save(updatedDetails);

    }

    /**
     * Marks a user as inactive (soft delete) based on their ID.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException If no active user exists with the given ID.
     */
    @Override
    public void deleteUserDetails(long id) throws UserNotFoundException {
        UserDetails userDetails = getUserDetails(id);
        userDetails.setActive(IN_ACTIVE);
        userRepository.save(userDetails);
    }

    /**
     * Prepares a UserDetails object for saving or updating.
     *
     * @param name       The name of the user.
     * @param phone      The phone number of the user.
     * @param email      The email address of the user.
     * @param address    The address of the user (can be null).
     * @param licenseNo  The license number of the user.
     * @param userTypeId The ID of the user type.
     * @param isActive   The active status of the user.
     * @return A prepared UserDetails object.
     */
    private UserDetails prepareUserDetails(String name, String phone, String email, Address address, String licenseNo, int userTypeId, int isActive) {
        UserDetails userDetails = new UserDetails();
        userDetails.setName(name);
        userDetails.setPhone(phone);
        userDetails.setEmail(email);
        userDetails.setAddress(address);
        userDetails.setLicenseNo(licenseNo);
        userDetails.setUserType(UserTypeEnum.computeUserType(userTypeId));
        userDetails.setActive(isActive);
        return userDetails;
    }
}
