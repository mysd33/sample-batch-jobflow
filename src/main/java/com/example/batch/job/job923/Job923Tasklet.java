package com.example.batch.job.job923;

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
import java.util.List;

import tools.jackson.databind.ObjectMapper;

/// Job923のTasklet<br>
///
/// コマンドライン実行例
///
/// ``````
///
/// java -jar app.jar --spring.profiles.active=production,log_default --spring.batch.job.name=job923 inputData="{\"jobResultList\":[\"result_job922_0\",\"result_job922_1\"]}"
/// ``````
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job923Tasklet implements Tasklet {

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
        appLogger.debug("Job923Tasklet実行[inputData:{}]", inputData);
        // Job922の処理結果取得
        Job923InputData job923InputData = objectMapper.readValue(inputData, Job923InputData.class);

        // Job922のMapでの多重処理結果取得
        List<String> resultList = job923InputData.getJobResultList();
        for (int i = 0; i < resultList.size(); i++) {
            appLogger.debug("Job922から受け取った処理結果[index:{}, result:{}]", i, resultList.get(i));
        }

        // 処理結果はダミーの値をセットしている。
        // 実際はJob923Taskletの処理結果をセットする。
        Job923ResultData job923ResultData = Job923ResultData.builder()
            .result("result_job923")
            .build();
        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job923ResultData);

        return RepeatStatus.FINISHED;
    }
}
