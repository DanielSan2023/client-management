package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.repository.SendEmailStep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final SendEmailStep sendEmailStep;
    private final JobLauncher jobLauncher;

    public BatchConfig(SendEmailStep sendEmailStep, JobLauncher jobLauncher) {
        this.sendEmailStep = sendEmailStep;
        this.jobLauncher = jobLauncher;
    }

    @Bean
    public Job sendEmailJob(JobRepository jobRepository) {
        return new JobBuilder("sendEmailJob")
                .repository(jobRepository)
                .start(sendEmailStep.sendEmailStep()
                .build();
    }

    @Scheduled(cron = "0 0 0 ? * MON") // Runs the job every Monday at midnight
    public void scheduleEmailJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        try {
            jobLauncher.run(sendEmailJob(null), jobParameters);
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}

