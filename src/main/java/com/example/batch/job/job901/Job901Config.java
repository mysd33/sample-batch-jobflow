package com.example.batch.job.job901;

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
 * Job901の定義<br>
 * Job901Taskletを実行するJobの例
 */
@Configuration
@RequiredArgsConstructor
public class Job901Config {
    private final Job901Tasklet job901Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /**
     * Job
     */
    @Bean
    Job job901(JobExecutionListener listener) {
        return new JobBuilder("job901", jobRepository)//
                .listener(listener)//
                .start(step90101())//
                .build();
    }

    /**
     * Step
     */
    @Bean
    Step step90101() {
        return new StepBuilder("step901_01", jobRepository)//
                .tasklet(job901Tasklet, transactionManager)//
                .build();
    }
}
