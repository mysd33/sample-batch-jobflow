package com.example.batch.job.job901;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Job901のTasklet<br>
 * 
 * コマンドライン実行例
 * 
 * <pre>{@code
 * java -jar app.jar --spring.profiles.active=production,log_default. --spring.batch.job.name=job901 inputData=input901 taskToken=xxxxx
 * }</pre>
 */
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job901Tasklet implements Tasklet {
    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);
    private final SfnTaskResultSender sfnTaskResultSender;

    // ジョブパラメータの例
    @Value("#{jobParameters['taskToken']}")
    private String taskToken;
    @Value("#{jobParameters['inputData']}")
    private String inputData;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        appLogger.debug("Job901Tasklet実行[inputData:{}]", inputData);
        // 処理結果はダミーの値をセットしている。
        // 実際はJob901Taskletの処理結果をセットする。
        Job901ResultData job901ResultData = Job901ResultData.builder().result("result_job901").build();
        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job901ResultData);

        return RepeatStatus.FINISHED;
    }
}
