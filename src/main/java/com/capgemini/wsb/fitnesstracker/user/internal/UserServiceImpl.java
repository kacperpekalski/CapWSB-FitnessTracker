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

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("User with ID " + userId + " has been deleted");
        userRepository.deleteById(userId);
    }

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

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findUsersByEmailFragment(String emailFragment) {
        return userRepository.findByEmailContainingIgnoreCase(emailFragment);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

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

}