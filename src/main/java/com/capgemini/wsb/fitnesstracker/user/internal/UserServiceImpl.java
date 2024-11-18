package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serive iplemeentation for managing users
 */

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Create a new user
     *
     * @param user user to create
     * @return created user
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Delete user by ID
     *
     * @param userId ID of the user to delete
     */
    @Override
    public void deleteUserById(Long userId) {
        log.info("User with ID " + userId + " has been deleted");
        userRepository.deleteById(userId);
    }

    /**
     * Update user by ID
     *
     * @param userId  ID of the user to update
     * @param userDto user data to update
     * @return updated user
     * @throws UserNotFoundException if user with given ID does not exist
     */
    @Override
    public User updateUser(Long userId, UserDto userDto) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (userDto.firstName() != null)
            updateUser.setFirstName(userDto.firstName());
        if (userDto.lastName() != null)
            updateUser.setLastName(userDto.lastName());
        if (userDto.birthdate() != null)
            updateUser.setBirthdate(userDto.birthdate());
        if (userDto.email() != null)
            updateUser.setEmail(userDto.email());

        return userRepository.save(updateUser);
    }

    /**
     * Get user by ID
     *
     * @param userId ID of the user to get
     * @return user with given ID
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Get user by email
     *
     * @param email email of the user to get
     * @return user with given email
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find users by email fragment
     *
     * @param emailFragment email fragment to search for
     * @return list of users with matching email fragment
     */
    @Override
    public List<User> findUsersByEmailFragment(String emailFragment) {
        return userRepository.findByEmailContainingIgnoreCase(emailFragment);
    }

    /**
     * Find all users
     *
     * @return list of all users
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find users by anything
     *
     * @param firstName first name of the user
     * @param lastName  last name of the user
     * @param birthdate birthdate of the user
     * @param email     email of the user
     * @return list of users matching the criteria
     */
    @Override
    public List<User> findUsersByAnything(String firstName, String lastName, LocalDate birthdate, String email) {
        List<User> users = new ArrayList<>();
        if (firstName != null) {
            users.addAll(userRepository.findByFirstName(firstName));
        }
        if (lastName != null) {
            users.addAll(userRepository.findByLastName(lastName));
        }
        if (birthdate != null) {
            users.addAll(userRepository.findByBirthdate(birthdate));
        }
        if (email != null) {
            userRepository.findByEmail(email).ifPresent(users::add);
        }
        return users;
    }

    /**
     * Find user by ID
     *
     * @param id ID of the user to find
     * @return user with given ID
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Find users older than given date
     *
     * @param time date to compare
     * @return list of users older than given date
     */
    @Override
    public List<User> findUsersOlderThanGivenDate(LocalDate time) {
        return userRepository.findByBirthdateBefore(time);
    }
}