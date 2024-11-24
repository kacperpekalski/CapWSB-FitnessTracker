package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TrainingReportScheduler {

    private final TrainingService trainingService;
    private final UserService userService;
    private final EmailSender emailSender;

    public TrainingReportScheduler(TrainingService trainingService, UserService userService, EmailSender emailSender) {
        this.trainingService = trainingService;
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendMonthlyReports() {
        userService.findAllUsers().forEach(user -> {
            int trainingCount = trainingService.countByUserIdAndStartTimeBetween(user.getId());

            String emailContent = "Cześć! " + ",\n\n"
                    + "W zeszłym miesiącu odbyłeś/aś " + trainingCount + " treningów.\n\n"
                    + "Pozdrawiamy,\nFitness Tracker";

//            EmailDto email = new EmailDto(user.getEmail(), "Miesięczne podsumowanie treningów", emailContent);
            EmailDto email = new EmailDto("marcin.szklarski.1999@gmail.com", "Miesięczne podsumowanie treningów", emailContent);

            emailSender.send(email);
        });
    }
}
