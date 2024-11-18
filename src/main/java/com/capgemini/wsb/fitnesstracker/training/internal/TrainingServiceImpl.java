package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.training.api.*;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserService userService;

    // Konstruktor z UserService
    public TrainingServiceImpl(TrainingRepository trainingRepository, UserService userService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
    }

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUser(User user) {
        return trainingRepository.findByUser(user);
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public List<Training> getFinishedTrainingsAfter(Date afterTime) {
        return trainingRepository.findByEndTimeAfter(afterTime);
    }

    public User getUserById(Long userId) {
        return userService.findUserById(userId);
    }

    @Transactional
    @Override
    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long trainingId, TrainingDto trainingDto) {
        Training updateTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        if (trainingDto.getStartTime() != null)
            updateTraining.setStartTime(trainingDto.getStartTime());
        if (trainingDto.getEndTime() != null)
            updateTraining.setEndTime(trainingDto.getEndTime());
        if (trainingDto.getActivityType() != null)
            updateTraining.setActivityType(trainingDto.getActivityType());
        if (trainingDto.getDistance() != 0)
            updateTraining.setDistance(trainingDto.getDistance());
        if (trainingDto.getAverageSpeed() != 0)
            updateTraining.setAverageSpeed(trainingDto.getAverageSpeed());

        return trainingRepository.save(updateTraining);
    }
}
