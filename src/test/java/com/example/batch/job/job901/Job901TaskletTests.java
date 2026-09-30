package com.example.batch.job.job901;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import java.security.SecureRandom;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.test.util.ReflectionTestUtils;
import tools.jackson.databind.json.JsonMapper;

class Job901TaskletTests {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void sendsRandomBoolean(boolean randomValue) throws Exception {
        SfnTaskResultSender sender = mock(SfnTaskResultSender.class);
        try (var randomFactory = mockConstruction(SecureRandom.class,
            (random, _) -> when(random.nextBoolean()).thenReturn(randomValue))) {
            Job901Tasklet tasklet = new Job901Tasklet(sender);
            ReflectionTestUtils.setField(tasklet, "inputData", "input901");
            ReflectionTestUtils.setField(tasklet, "taskToken", "test-token");

            RepeatStatus status = tasklet.execute(mock(StepContribution.class),
                mock(ChunkContext.class));

            assertEquals(RepeatStatus.FINISHED, status);
            verify(randomFactory.constructed().getFirst()).nextBoolean();
            verify(sender).sendTaskSuccess("test-token",
                Job901ResultData.builder().result(randomValue).build());
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void serializesAndReadsBooleanResult(boolean result) {
        var objectMapper = JsonMapper.builder().build();
        Job901ResultData resultData = Job901ResultData.builder().result(result).build();
        String json = "{\"result\":" + result + "}";

        assertEquals(json, objectMapper.writeValueAsString(resultData));
        assertEquals(result, objectMapper.readValue(json, Job901ResultData.class).isResult());
    }
}
