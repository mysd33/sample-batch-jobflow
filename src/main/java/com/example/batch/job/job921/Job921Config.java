package com.example.batch.job.job921;

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

/// Job921の定義<br>
/// Job921Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job921Config {
    private final Job921Tasklet job921Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job921(JobExecutionListener listener) {
        return new JobBuilder("job921", jobRepository)//
                .listener(listener)//
                .start(step92101())//
                .build();
    }

    /// Step
    @Bean
    Step step92101() {
        return new StepBuilder("step921_01", jobRepository)//
                .tasklet(job921Tasklet, transactionManager)//
                .build();
    }
}
