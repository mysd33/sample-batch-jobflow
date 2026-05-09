package com.example.batch.job.job913;

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

/// Job913の定義<br>
/// Job913Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job913Config {
    private final Job913Tasklet job913Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job913(JobExecutionListener listener) {
        return new JobBuilder("job913", jobRepository)//
                .listener(listener)//
                .start(step91301())//
                .build();
    }

    /// Step
    @Bean
    Step step91301() {
        return new StepBuilder("step913_01", jobRepository)//
                .tasklet(job913Tasklet, transactionManager)//
                .build();
    }
}
