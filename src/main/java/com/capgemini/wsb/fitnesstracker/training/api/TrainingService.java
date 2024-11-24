package com.capgemini.wsb.fitnesstracker.training.api;


import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training addTraining(Training training);
    List<Training> getAllTrainings();
    List<Training> getTrainingsByUser(User user);
    List<Training> getTrainingsByActivityType(ActivityType activityType);
    List<Training> getFinishedTrainingsAfter(Date afterTime);
    Training updateTraining(Long trainingId, TrainingDto trainingDto);
    int countByUserIdAndStartTimeBetween(Long userId);
}
