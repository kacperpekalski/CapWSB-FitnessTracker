package com.capgemini.wsb.fitnesstracker.user.api;


import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */

public interface UserService {

    User createUser(User user);

    void deleteUserById(Long userId);

    List<User> findUsersByAnything
            (String firstName, String lastName, LocalDate birthdate, String email);

    List<User> findUsersByEmailFragment(String emailFragment);

    User updateUser(Long userId, UserDto userDto) throws UserNotFoundException;
}
