package com.example.batch.job.job922;

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

/// Job922のTasklet<br>
///
/// コマンドライン実行例
///
/// ``````
///
/// java -jar app.jar --spring.profiles.active=production,log_default --spring.batch.job.name=job922 inputData="{\"result\":\"result_921_0\"}"
/// ``````
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job922Tasklet implements Tasklet {

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
        appLogger.debug("Job922Tasklet実行[inputData:{}]", inputData);

        Job922InputData job922InputData = objectMapper.readValue(inputData, Job922InputData.class);
        String job921result = job922InputData.getResult();

        appLogger.debug("Job921から受け取った処理結果[result:{}]", job921result);

        // job921の値は「result_921_{index}」の形式なので{index}の部分を取得
        String indexStr = job921result.substring("result_921_".length());

        // 処理結果は、indexの値を引き継いで、ダミーの値をセットしている。
        // 実際はJob922Taskletの処理結果をセットする。
        Job922ResultData job922ResultData = Job922ResultData.builder()
            .result("result_job922_" + indexStr).build();

        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job922ResultData);

        return RepeatStatus.FINISHED;
    }
}
