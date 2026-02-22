package com.example.batch.job.job902;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/**
 * Job902の定義<br>
 * Job902Taskletを実行するJobの例
 */
@Configuration
@RequiredArgsConstructor
public class Job902Config {
    private final Job902Tasklet job902Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /**
     * Job
     */
    @Bean
    Job job902(JobExecutionListener listener) {
        return new JobBuilder("job902", jobRepository)//
                .listener(listener)//
                .start(step90201())//
                .build();
    }

    /**
     * Step
     */
    @Bean
    Step step90201() {
        return new StepBuilder("step902_01", jobRepository)//
                .tasklet(job902Tasklet, transactionManager)//
                .build();
    }
}
