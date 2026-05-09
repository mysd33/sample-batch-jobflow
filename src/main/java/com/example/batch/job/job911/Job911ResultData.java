package com.example.batch.job.job911;

import lombok.Builder;
import lombok.Value;

/// Job911Taskletの処理結果を格納するクラス<br>
///
/// ジョブフローの後続ジョブへ結果を渡すためのクラスの例
@Value
@Builder
public class Job911ResultData {
    // 処理結果の例
    String result;
}
