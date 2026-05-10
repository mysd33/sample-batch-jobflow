package com.example.batch.job.job922;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/// Job922Taskletの入力データを格納するクラス<br>
///
/// Job921の処理結果(resultList)を受け取るためのクラス
@Value
@Builder
@Jacksonized
public class Job922InputData {

    // Job921から受け取る処理結果
    // 処理結果リストから、Step FunctionsのMapを経由して、1要素のみを受けとる
    String result;
}
