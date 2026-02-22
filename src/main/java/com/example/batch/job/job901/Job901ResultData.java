package com.example.batch.job.job901;

import lombok.Builder;
import lombok.Value;

/**
 * Job901Taskletの処理結果を格納するクラス<br>
 * 
 * ジョブフローの後続ジョブへ結果を渡すためのクラスの例
 */
@Value
@Builder
public class Job901ResultData {
    // 処理結果の例
    private final String result;
}
