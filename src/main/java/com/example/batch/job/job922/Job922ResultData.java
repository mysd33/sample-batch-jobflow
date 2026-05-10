package com.example.batch.job.job922;

import lombok.Builder;
import lombok.Value;

/// Job922Taskletの処理結果を格納するクラス<br>
///
/// ジョブフローの後続ジョブへ結果を渡すためのクラスの例
@Value
@Builder
public class Job922ResultData {
    // 処理結果の例
    String result;
}
