package com.example.batch.job.job921;


import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Job921ResultDataList {

    // 処理結果のリスト
    List<Job921ResultData> list;
}
