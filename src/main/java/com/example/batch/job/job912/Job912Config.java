package com.example.batch.job.job912;

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

/// Job912の定義<br>
/// Job912Taskletを実行するJobの例
@Configuration
@RequiredArgsConstructor
public class Job912Config {
    private final Job912Tasklet job912Tasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    /// Job
    @Bean
    Job job912(JobExecutionListener listener) {
        return new JobBuilder("job912", jobRepository)//
                .listener(listener)//
                .start(step91201())//
                .build();
    }

    /// Step
    @Bean
    Step step91201() {
        return new StepBuilder("step912_01", jobRepository)//
                .tasklet(job912Tasklet, transactionManager)//
                .build();
    }
}
