package com.example.batch.job.job903;

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

/// Job903の定義<br>
/// Job903Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job903Config {
    private final Job903Tasklet job903Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job903(JobExecutionListener listener) {
        return new JobBuilder("job903", jobRepository)//
                .listener(listener)//
                .start(step90301())//
                .build();
    }

    /// Step
    @Bean
    Step step90301() {
        return new StepBuilder("step903_01", jobRepository)//
                .tasklet(job903Tasklet, transactionManager)//
                .build();
    }
}
