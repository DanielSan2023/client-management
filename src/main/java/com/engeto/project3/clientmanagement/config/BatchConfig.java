package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.config.email.LicenseFileWriter;
import com.engeto.project3.clientmanagement.config.email.LicenseProcessor;
import com.engeto.project3.clientmanagement.config.email.LicenseReader;
import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@AllArgsConstructor
@Configuration
public class BatchConfig {

    private final Reader reader;
    private final Processor processor;
    private final Writer writer;
    private final LicenseReader licenseReader;
    private final LicenseProcessor licenseProcessor;
    private final LicenseFileWriter licenseFileWriterWriter;

    @Bean
    public Job sendEmailJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("sendEmailJob", jobRepository)
                .start(step1(jobRepository, platformTransactionManager))
                .next(step2(jobRepository, platformTransactionManager))
                .build();

    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<ClientInfo, ClientInfo>chunk(1, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step2", jobRepository)
                .<ClientLicense, ClientLicense>chunk(1, platformTransactionManager)
                .reader(licenseReader)
                .processor(licenseProcessor)
                .writer(licenseFileWriterWriter)
                .build();
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        //two threads run concurrently for batch processing
        simpleAsyncTaskExecutor.setConcurrencyLimit(2);
        return simpleAsyncTaskExecutor;
    }

//    @Scheduled(cron = "0 0 0 ? * MON") // Runs the job every Monday at midnight
//    public void scheduleEmailJob() {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("jobId", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//        try {
//            jobLauncher.run(sendEmailJob(), jobParameters);
//        } catch (JobExecutionException e) {
//            e.printStackTrace();
//        }
//    }
}

