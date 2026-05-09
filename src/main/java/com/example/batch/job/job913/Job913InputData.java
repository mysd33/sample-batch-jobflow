package com.example.batch.job.job913;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/// Job913Taskletの入力データを格納するクラス<br>
///
/// Job901とJob902の処理結果をJSON文字列の配列で受け取るためのクラスの例
@Value
@Builder
@Jacksonized
public class Job913InputData {
    // Job901とJob902の処理結果JSON文字列のリスト
    List<String> jobResultList;
}
