package com.example.batch.job.job902;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.batch.job.job901.Job901ResultData;
import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Job902のTasklet<br>
 * 
 * コマンドライン実行例
 * 
 * <pre>{@code
 * java -jar app.jar --spring.profiles.active=production,log_default --spring.batch.job.name=job902 inputData="{\"result\":\"result_job901\"}" taskToken=xxxxx
 * }</pre>
 */
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job902Tasklet implements Tasklet {
    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);
    private final SfnTaskResultSender sfnTaskResultSender;
    private final ObjectMapper objectMapper;

    // ジョブパラメータの例
    @Value("#{jobParameters['taskToken']}")
    private String taskToken;
    @Value("#{jobParameters['inputData']}")
    private String inputData;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        appLogger.debug("Job902Tasklet実行[inputData:{}]", inputData);
        // Job901の処理結果取得
        Job901ResultData job901ResultData = objectMapper.readValue(inputData, Job901ResultData.class);
        appLogger.debug("Job901の処理結果[result:{}]", job901ResultData.getResult());

        // 処理結果はダミーの値をセットしている。
        // 実際はJob902Taskletの処理結果をセットする。
        Job902ResultData job902ResultData = Job902ResultData.builder().result("result_job902").build();
        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job902ResultData);

        return RepeatStatus.FINISHED;
    }
}
