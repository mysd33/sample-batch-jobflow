package com.example.batch.job.job913;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/// Job913Taskletの入力データを格納するクラス<br>
///
/// Step FunctionsのParallelでJob901とJob902の並列の処理結果をJSON文字列の配列で受け取るためのクラスの例
@Value
@Builder
@Jacksonized
public class Job913InputDataList {

    List<Job913InputData> jobResultList;
}
