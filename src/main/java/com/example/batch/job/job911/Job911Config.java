package com.example.batch.job.job911;

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

/// Job911の定義<br>
/// Job911Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job911Config {
    private final Job911Tasklet job911Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job911(JobExecutionListener listener) {
        return new JobBuilder("job911", jobRepository)//
                .listener(listener)//
                .start(step91101())//
                .build();
    }

    /// Step
    @Bean
    Step step91101() {
        return new StepBuilder("step911_01", jobRepository)//
                .tasklet(job911Tasklet, transactionManager)//
                .build();
    }
}
