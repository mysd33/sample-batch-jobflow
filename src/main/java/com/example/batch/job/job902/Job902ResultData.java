package com.example.batch.job.job902;

import lombok.Builder;
import lombok.Value;

/**
 * Job902Taskletの処理結果を格納するクラス<br>
 * 
 * ジョブフローの後続ジョブへ結果を渡すためのクラスの例
 */
@Value
@Builder
public class Job902ResultData {
    // 処理結果の例
    private final String result;
}
