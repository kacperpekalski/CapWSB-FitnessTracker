package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API controller for managing trainings.
 */
@RestController
@RequestMapping("/v1/trainings")
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserService userService;

    public TrainingController(TrainingServiceImpl trainingService, TrainingMapper trainingMapper, UserService userService) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.userService = userService;
    }

    /**
     * Add a new training to the system.
     *
     * @param trainingDto the training data transfer object
     *
     * @return the created training with status 201
     */
    @PostMapping
    public ResponseEntity<TrainingDto> addTraining(@RequestBody TrainingDto trainingDto) {
        if (trainingDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is missing in the request");
        }
        User user = userService.findUserById(trainingDto.getUserId());
        Training training = trainingMapper.toEntity(trainingDto, user);
        Training savedTraining = trainingService.addTraining(training);
        TrainingDto savedTrainingDto = trainingMapper.toDto(savedTraining);
        return ResponseEntity.status(201).body(savedTrainingDto);
    }

    /**
     * Get all trainings in the system.
     *
     * @return list of all trainings
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return list of trainings associated with the user
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        User user = trainingService.getUserById(userId); // Retrieve user by ID
        return trainingService.getTrainingsByUser(user)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get trainings finished after a specified date.
     * This endpoint retrieves all trainings that were completed after the provided date.
     * Example of usage:
     * <pre>
     * GET /v1/trainings/finished/2024-01-16
     * </pre>
     *
     * @param afterTime the date in format yyyy-MM-dd (e.g., 2024-01-16)
     * @return list of trainings finished after the given date
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(@PathVariable String afterTime) {
        Date afterDate = java.sql.Date.valueOf(afterTime); // Parse input to Date
        return trainingService.getFinishedTrainingsAfter(afterDate)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get trainings by activity type.
     * This endpoint allows filtering trainings by their activity type.
     * Example of usage:
     * GET /v1/trainings/activityType?activityType=RUNNING
     *
     * @param activityType the activity type to filter (e.g., RUNNING, CYCLING)
     * @return list of trainings matching the specified activity type
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing training.
     *
     * @param trainingId  the ID of the training to be updated
     * @param trainingDto the updated training data
     * @return the updated training
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        return trainingService.updateTraining(trainingId, trainingDto);
    }
}
