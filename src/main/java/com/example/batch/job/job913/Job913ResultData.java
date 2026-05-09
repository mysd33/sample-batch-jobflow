package com.example.batch.job.job913;

import lombok.Builder;
import lombok.Value;

/// Job913Taskletの処理結果を格納するクラス<br>
///
/// ジョブフローの後続ジョブへ結果を渡すためのクラスの例
@Value
@Builder
public class Job913ResultData {
    // 処理結果の例
    String result;
}
