package com.capgemini.wsb.fitnesstracker.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reports")
public class TrainingReportController {

    private final TrainingReportScheduler reportScheduler;

    public TrainingReportController(TrainingReportScheduler reportScheduler) {
        this.reportScheduler = reportScheduler;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendReportManually() {
        reportScheduler.sendMonthlyReports();
        return ResponseEntity.ok("Raport został wysłany!");
    }
}
