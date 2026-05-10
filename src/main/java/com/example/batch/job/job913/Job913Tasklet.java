package com.example.batch.job.job913;

import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

/// Job913のTasklet<br>
///
/// コマンドライン実行例
///
/// ``````
///
/// java -jar app.jar --spring.profiles.active=production,log_default --spring.batch.job.name=job913 inputData="{\"jobResultList\":[\"result_job901\",\"result_job902\"]}"
/// ``````
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job913Tasklet implements Tasklet {

    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);
    private final SfnTaskResultSender sfnTaskResultSender;
    private final ObjectMapper objectMapper;

    // ジョブパラメータの例
    @Value("#{jobParameters['inputData']}")
    private String inputData;

    // StepFunctionsのタスクトークンはOS環境変数TASK_TOKENから取得
    @Value("${TASK_TOKEN:}")
    private String taskToken;

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution,
        @NonNull ChunkContext chunkContext) throws Exception {
        appLogger.debug("Job913Tasklet実行[inputData:{}]", inputData);
        // Job911とJob912の処理結果取得
        Job913InputDataList job913InputDataList = objectMapper.readValue(inputData,
            Job913InputDataList.class);

        // Job911の処理結果取得
        appLogger.debug("Job911から受け取った処理結果[result:{}]",
            job913InputDataList.getJobResultList().getFirst().getResult());

        // Job912の処理結果取得
        appLogger.debug("Job912から受け取った処理結果[result:{}]",
            job913InputDataList.getJobResultList().get(1).getResult());

        // 処理結果はダミーの値をセットしている。
        // 実際はJob913Taskletの処理結果をセットする。
        Job913ResultData job913ResultData = Job913ResultData.builder().result("result_job913")
            .build();
        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job913ResultData);

        return RepeatStatus.FINISHED;
    }
}
