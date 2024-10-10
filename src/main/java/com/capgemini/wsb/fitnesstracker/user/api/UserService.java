package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */

public interface UserService {

    User createUser(User user);


    List<User> findUsersByCriteria
            (String firstName, String lastName, LocalDate birthdate, String email);

}
