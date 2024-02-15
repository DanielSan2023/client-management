package com.engeto.project3.clientmanagement.config;


import com.engeto.project3.clientmanagement.domain.ClientInfo;
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

@Configuration
public class BatchConfig {

    private final Reader reader;
    private final Processor processor;
    private final Writer writer;

    public BatchConfig(Reader reader, Processor processor, Writer writer) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Job sendEmailJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("sendEmailJob", jobRepository)
                .start(step1(jobRepository, platformTransactionManager))
                .build();

    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<ClientInfo, ClientInfo>chunk(1, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
               // .taskExecutor(taskExecutor())
                .build();
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        //two threads run concurrently for batch processing
        simpleAsyncTaskExecutor.setConcurrencyLimit(2);
        return simpleAsyncTaskExecutor;
    }

}
