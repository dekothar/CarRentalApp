package com.design.car.rental.system.carrentalsystemapp.services.impl;


import com.design.car.rental.system.carrentalsystemapp.exceptions.UserNotFoundException;
import com.design.car.rental.system.carrentalsystemapp.models.Address;
import com.design.car.rental.system.carrentalsystemapp.models.UserDetails;

import java.util.List;


public interface UserService {

    /**
     * Adds a new user to the system.
     * - Creates a new user with the provided details.
     * - Saves the user and their address (if provided) to the database.
     * - Marks the user as active.
     *
     * @param name       The name of the user.
     * @param phone      The phone number of the user.
     * @param email      The email address of the user.
     * @param address    The address of the user (can be null).
     * @param licenseNo  The license number of the user.
     * @param userTypeId The ID representing the user's type.
     * @return The saved UserDetails object containing the user information.
     */
    UserDetails addUser(String name, String phone, String email, Address address, String licenseNo, int userTypeId);

    /**
     * Retrieves a list of active users based on their user type.
     *
     * @param userTypeId The ID representing the user type to filter by.
     * @return A list of UserDetails objects for users with the specified type and active status.
     */
    List<UserDetails> getUserDetailsByUserType(int userTypeId);

    /**
     * Retrieves the details of a specific active user by their user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserDetails object containing the user's information.
     * @throws UserNotFoundException If no active user exists with the specified ID.
     */
    UserDetails getUserDetailsByUserId(long userId) throws UserNotFoundException;

    /**
     * Retrieves a list of all active users in the system.
     *
     * @return A list of UserDetails objects representing all active users.
     */
    List<UserDetails> getAllActiveUserDetails();

    /**
     * Updates the details of an existing active user.
     * - Ensures the user exists and is active before updating.
     * - Updates the user's information with the provided details while retaining their ID and active status.
     *
     * @param id         The ID of the user to update.
     * @param name       The updated name of the user.
     * @param phone      The updated phone number of the user.
     * @param email      The updated email address of the user.
     * @param address    The updated address of the user (can be null).
     * @param licenseNo  The updated license number of the user.
     * @param userTypeId The updated ID representing the user's type.
     * @return The updated UserDetails object containing the new user information.
     * @throws UserNotFoundException If no active user exists with the specified ID.
     */
    UserDetails updateUserDetails(long id, String name, String phone, String email, Address address, String licenseNo, int userTypeId) throws UserNotFoundException;

    /**
     * Marks a user as inactive (soft delete) in the system.
     * - Ensures the user exists and is active before marking them inactive.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException If no active user exists with the specified ID.
     */
    void deleteUserDetails(long id) throws UserNotFoundException;

}
