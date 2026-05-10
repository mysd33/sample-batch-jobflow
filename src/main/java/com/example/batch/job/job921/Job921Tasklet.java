package com.example.batch.job.job921;

import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import com.example.fw.common.logging.ApplicationLogger;
import com.example.fw.common.logging.LoggerFactory;
import java.util.List;
import java.util.stream.IntStream;
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

/// Job921のTasklet<br>
///
/// コマンドライン実行例
///
/// ``````
///
/// java -jar app.jar --spring.profiles.active=production,log_default --spring.batch.job.name=job921 inputData=input921
/// ``````
@StepScope
@Component
@Slf4j
@RequiredArgsConstructor
public class Job921Tasklet implements Tasklet {

    private static final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);
    private final SfnTaskResultSender sfnTaskResultSender;

    // ジョブパラメータの例
    @Value("#{jobParameters['inputData']}")
    private String inputData;

    // StepFunctionsのタスクトークンはOS環境変数TASK_TOKENから取得
    @Value("${TASK_TOKEN:}")
    private String taskToken;

    // Step FunctionsのMapサイズ設定
    @Value("${example.job921.map_size:10}")
    private int mapSize;

    @Override
    public RepeatStatus execute(@NonNull StepContribution contribution,
        @NonNull ChunkContext chunkContext) throws Exception {
        appLogger.debug("Job921Tasklet実行[inputData:{}, mapSize:{}]", inputData, mapSize);

        // map_size の数分、result_921_{index} のリストを作成する。
        List<Job921ResultData> resultList = IntStream.range(0, mapSize)
            .mapToObj(index -> Job921ResultData.builder().result("result_921_" + index).build())
            .toList();

        Job921ResultDataList job921ResultDataList = Job921ResultDataList.builder().list(resultList)
            .build();

        // ジョブフローの後続ジョブへ結果を渡すために、StepFunctionsのタスクの実行成功を送信する
        sfnTaskResultSender.sendTaskSuccess(taskToken, job921ResultDataList);

        return RepeatStatus.FINISHED;
    }
}
