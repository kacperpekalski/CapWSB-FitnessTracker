package com.capgemini.wsb.fitnesstracker.user.api;


import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */

public interface UserService {

    /**
     * Create a new user in the system.
     *
     * @param user the user to be created
     * @return the created user
     */
    User createUser(User user);

    /**
     * Delete a user from the system.
     *
     * @param userId the ID of the user to be deleted
     */
    void deleteUserById(Long userId);

    /**
     * Find all users in the system.
     *
     * @return list of all users in the system
     */
    List<User> findUsersByAnything
            (String firstName, String lastName, LocalDate birthdate, String email);

    /**
     * Find users by email fragment.
     *
     * @param emailFragment the fragment of the email to search for
     * @return list of users with email containing the given fragment
     */
    List<User> findUsersByEmailFragment(String emailFragment);

    /**
     * Update a user in the system.
     *
     * @param userId the ID of the user to be updated
     * @param userDto the updated user data
     * @return the updated user
     * @throws UserNotFoundException if the user is not found
     */
    User updateUser(Long userId, UserDto userDto) throws UserNotFoundException;

    /**
     * Find a user by ID.
     *
     * @param userId the ID of the user to find
     * @return the user with the given ID
     */
    User findUserById(Long userId);

    /**
     * Find user older than given date.
     *
     * @param time the date to compare with
     * @return list of users older than the given date
     */
    List<User> findUsersOlderThanGivenDate(LocalDate time);
}
