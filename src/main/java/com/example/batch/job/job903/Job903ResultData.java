package com.example.batch.job.job903;

import lombok.Builder;
import lombok.Value;

/// Job903Taskletの処理結果を格納するクラス<br>
///
/// ジョブフローの後続ジョブへ結果を渡すためのクラスの例
@Value
@Builder
public class Job903ResultData {
    // 処理結果の例
    String result;
}
