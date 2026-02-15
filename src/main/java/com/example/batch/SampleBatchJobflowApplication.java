package com.example.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleBatchJobflowApplication {

    public static void main(String[] args) {
        // コマンドライン実行の場合のmainメソッドの定義
        // 参考: https://spring.io/guides/gs/batch-processing
        System.exit(SpringApplication.exit(SpringApplication.run(SampleBatchJobflowApplication.class, args)));
    }

}
