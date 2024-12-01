package com.design.car.rental.system.carrentalsystemapp.controllers;

import com.design.car.rental.system.carrentalsystemapp.dtos.UserRequestDto;
import com.design.car.rental.system.carrentalsystemapp.dtos.UserResponseDto;
import com.design.car.rental.system.carrentalsystemapp.exceptions.UserNotFoundException;
import com.design.car.rental.system.carrentalsystemapp.models.UserDetails;
import com.design.car.rental.system.carrentalsystemapp.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Define allowed user type IDs in a more flexible way
    private static final List<Integer> VALID_USER_TYPE_IDS = List.of(0, 1, 2);

    private static final String RECORDS_DELETED="Record deleted successfully from the database";

    /**
     * Creates a new user based on the provided details.
     *
     * @param userRequestDto The user details to create a new user.
     * @return ResponseEntity containing the created user's response or a bad request status.
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        if (isInvalidUserRequest(userRequestDto)) {
            return ResponseEntity.badRequest().build();
        }
        UserDetails userDetails = userService.addUser(
                userRequestDto.getName(),
                userRequestDto.getPhone(),
                userRequestDto.getEmail(),
                userRequestDto.getAddress(),
                userRequestDto.getLicenseNo(),
                userRequestDto.getUserTypeId()
        );
        UserResponseDto userResponseDto = computeUserResponseDetails(userDetails);
        userResponseDto.setUserId(userDetails.getId());
        return ResponseEntity.ok(userResponseDto);
    }

    /**
     * Retrieves all users of a specific user type.
     *
     * @param userTypeId The user type ID to filter users.
     * @return ResponseEntity containing the list of users or a bad request status.
     */
    @GetMapping("/userType/{userTypeId}")
    public ResponseEntity<List<UserResponseDto>> getUserDetailsByUserType(@PathVariable int userTypeId) {
        if (!VALID_USER_TYPE_IDS.contains(userTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        List<UserDetails> userDetailsList = userService.getUserDetailsByUserType(userTypeId);
        List<UserResponseDto> userResponseDtos = userDetailsList.stream()
                .map(this::computeUserResponseDetailsWithId)
                .toList();
        return ResponseEntity.ok(userResponseDtos);
    }

    /**
     * Retrieves user details for a specific user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user's response or an error status.
     * @throws UserNotFoundException If the user is not found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserDetailsByUserId(@PathVariable long userId) throws UserNotFoundException {
        UserDetails userDetails = userService.getUserDetailsByUserId(userId);
        UserResponseDto userResponseDto = computeUserResponseDetailsWithId(userDetails);
        return ResponseEntity.ok(userResponseDto);
    }

    /**
     * Retrieves all active users.
     *
     * @return ResponseEntity containing the list of all active users.
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserDetails> userDetailsList = userService.getAllActiveUserDetails();
        List<UserResponseDto> userResponseDtos = userDetailsList.stream()
                .map(this::computeUserResponseDetailsWithId)
                .toList();
        return ResponseEntity.ok(userResponseDtos);
    }

    /**
     * Updates the details of a specific user.
     *
     * @param userId         The ID of the user to update.
     * @param userRequestDto The updated user details.
     * @return ResponseEntity containing the updated user's response or an error status.
     * @throws UserNotFoundException If the user is not found.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUserDetails(@PathVariable long userId, @RequestBody UserRequestDto userRequestDto) throws UserNotFoundException {
        UserDetails userDetails = userService.updateUserDetails(
                userId,
                userRequestDto.getName(),
                userRequestDto.getPhone(),
                userRequestDto.getEmail(),
                userRequestDto.getAddress(),
                userRequestDto.getLicenseNo(),
                userRequestDto.getUserTypeId()
        );
        UserResponseDto userResponseDto = computeUserResponseDetailsWithId(userDetails);
        return ResponseEntity.ok(userResponseDto);
    }

    /**
     * Deletes (soft deletes) a user based on their ID.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity containing the success message or an error status.
     * @throws UserNotFoundException If the user is not found.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserDetails(@PathVariable long userId) throws UserNotFoundException {
        userService.deleteUserDetails(userId);
        return ResponseEntity.ok(RECORDS_DELETED);
    }

    /**
     * Converts a UserDetails object to a UserResponseDto and sets the user ID.
     *
     * @param userDetails The UserDetails object.
     * @return The UserResponseDto object.
     */
    private UserResponseDto computeUserResponseDetailsWithId(UserDetails userDetails) {
        UserResponseDto responseDto = computeUserResponseDetails(userDetails);
        responseDto.setUserId(userDetails.getId());
        return responseDto;
    }

    /**
     * Validates the user request details for creation.
     *
     * @param userRequestDto The user request details.
     * @return True if the request is invalid, false otherwise.
     */
    private boolean isInvalidUserRequest(UserRequestDto userRequestDto) {
        return userRequestDto.getName() == null || userRequestDto.getEmail() == null ||
                userRequestDto.getLicenseNo() == null || !VALID_USER_TYPE_IDS.contains(userRequestDto.getUserTypeId());
    }

    /**
     * Converts a UserDetails object to a UserResponseDto.
     *
     * @param userDetails The UserDetails object.
     * @return The UserResponseDto object.
     */
    private static UserResponseDto computeUserResponseDetails(UserDetails userDetails) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(userDetails.getName());
        userResponseDto.setPhone(userDetails.getPhone());
        userResponseDto.setEmail(userDetails.getEmail());
        userResponseDto.setAddress(userDetails.getAddress());
        userResponseDto.setLicenseNo(userDetails.getLicenseNo());
        return userResponseDto;
    }
}


