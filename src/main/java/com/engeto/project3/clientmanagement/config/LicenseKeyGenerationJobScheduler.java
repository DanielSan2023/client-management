package com.engeto.project3.clientmanagement.config;

import org.springframework.stereotype.Component;

@Component
public class LicenseKeyGenerationJobScheduler {
//
//    private final Job licenseKeyGenerationJob;
//    private final JobLauncher jobLauncher;
//
//    //@Autowired
//    public LicenseKeyGenerationJobScheduler(Job licenseKeyGenerationJob, JobLauncher jobLauncher) {
//        this.licenseKeyGenerationJob = licenseKeyGenerationJob;
//        this.jobLauncher = jobLauncher;
//    }
//
//    @Scheduled(cron = "0 0 0 * * *") // každý deň o polnoci
//    public void scheduleLicenseKeyGeneration() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("JobID", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//        jobLauncher.run(licenseKeyGenerationJob, jobParameters);
//    }
}
