package com.engeto.project3.clientmanagement.config.email;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BatchScheduler {

    private JobLauncher jobLauncher;
    private Job sendEmailJob;

    @Scheduled(cron = "0 0 0 ? * MON") // Runs the job every Monday at midnight
    public void scheduleEmailJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        try {
            jobLauncher.run(sendEmailJob, jobParameters);
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}
