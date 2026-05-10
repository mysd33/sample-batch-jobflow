package com.example.batch.job.job923;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

/// Job923の定義<br>
/// Job923Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job923Config {
    private final Job923Tasklet job923Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job923(JobExecutionListener listener) {
        return new JobBuilder("job923", jobRepository)//
                .listener(listener)//
                .start(step92301())//
                .build();
    }

    /// Step
    @Bean
    Step step92301() {
        return new StepBuilder("step923_01", jobRepository)//
                .tasklet(job923Tasklet, transactionManager)//
                .build();
    }
}
