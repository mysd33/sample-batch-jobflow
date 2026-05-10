package com.example.batch.job.job922;

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

/// Job922の定義<br>
/// Job922Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job922Config {
    private final Job922Tasklet job922Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job922(JobExecutionListener listener) {
        return new JobBuilder("job922", jobRepository)//
                .listener(listener)//
                .start(step92201())//
                .build();
    }

    /// Step
    @Bean
    Step step92201() {
        return new StepBuilder("step922_01", jobRepository)//
                .tasklet(job922Tasklet, transactionManager)//
                .build();
    }
}
