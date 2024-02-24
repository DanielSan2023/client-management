package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.config.email.LicenseFileWriter;
import com.engeto.project3.clientmanagement.config.email.LicenseProcessor;
import com.engeto.project3.clientmanagement.config.email.LicenseReader;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

@AllArgsConstructor
@Configuration
public class BatchConfig {

    private final LicenseReader licenseReader;
    private final LicenseProcessor licenseProcessor;
    private final LicenseFileWriter licenseFileWriterWriter;
    private final JobLauncher jobLauncher;

    @Bean
    public Job sendEmailJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("sendEmailJob", jobRepository)
                .start(step1(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<ClientLicense, ClientLicense>chunk(20, platformTransactionManager)
                .reader(licenseReader)
                .processor(licenseProcessor)
                .writer(licenseFileWriterWriter)
                .build();
    }

}
