package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserBasicDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API controller for managing users.
 */

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


    /**
     * Get all users from the system with detailed information.
     *
     * @return list of all users in the system
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


    /**
     * Get all users from the system with basic information.
     *
     * @return list of all users in the system
     */
    @GetMapping("/simple")
    public List<UserBasicDto> getUserBasic() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

    /**
     * Add a new user to the system.
     *
     * @param userDto the user data transfer object
     * @return the created user
     * @throws InterruptedException if the thread is interrupted
     */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        //System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    /**
     * Delete a user by id.
     *
     * @param id the id of the user to delete
     * @return response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Update a user by id.
     *
     * @param id the id of the user to update
     * @param userDto the user data transfer object
     * @return the updated user
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * Get users by criteria.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param birthdate the birthdate of the user
     * @param email the email of the user
     * @return list of users matching the criteria
     */
    @GetMapping("/search")
    public List<UserDto> getUsersByCriteria(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate birthdate,
            @RequestParam(required = false) String email) {
        System.out.println("Wyszukiwanie: " + firstName + ", lastName: " + lastName + ", birthdate: " + birthdate + ", email: " + email);
        return userService.findUsersByAnything(firstName, lastName, birthdate, email)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Get users by email fragment.
     *
     * @param email the email fragment to search for
     * @return list of users with matching email fragment
     */
    @GetMapping("/email")
    public List<Map<String, Object>> findUsersByEmailFragment(@RequestParam String email) {
        List<User> users = userService.findUsersByEmailFragment(email.toLowerCase());
        List<Map<String, Object>> result = new ArrayList<>();

        for (User user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("email", user.getEmail());
            result.add(userMap);
        }
        return result;
    }

    /**
     * Get user by id.
     *
     * @param id the id of the user
     * @return the user
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Get users older than given date.
     *
     * @param time the date to compare
     * @return list of users older than given date
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThanGivenDate(@PathVariable LocalDate time) {
        return userService.findUsersOlderThanGivenDate(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}