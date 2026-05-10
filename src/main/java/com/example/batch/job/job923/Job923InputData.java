package com.example.batch.job.job923;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/// Job923Taskletの入力データを格納するクラス<br>
///
/// Step FunctionsでのMapのJob922の多重実行の処理結果をまとめた文字列のリスト
@Value
@Builder
@Jacksonized
public class Job923InputData {

    // Step FunctionsでのMapのJob922の多重実行の処理結果をまとめた文字列のリスト
    String result;

}
