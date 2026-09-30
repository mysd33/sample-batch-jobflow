package com.example.batch.job.job903;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.example.fw.batch.jobflow.sfn.SfnTaskResultSender;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.test.util.ReflectionTestUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

class Job903TaskletTests {

    private final SfnTaskResultSender sender = mock(SfnTaskResultSender.class);
    private final Job903Tasklet tasklet = new Job903Tasklet(sender, JsonMapper.builder().build());

    @Test
    void sendsJob903ResultAfterReadingJob902Result() throws Exception {
        ReflectionTestUtils.setField(tasklet, "inputData", "{\"result\":\"result_job902\"}");
        ReflectionTestUtils.setField(tasklet, "taskToken", "test-token");

        RepeatStatus status = tasklet.execute(mock(StepContribution.class),
            mock(ChunkContext.class));

        assertEquals(RepeatStatus.FINISHED, status);
        verify(sender).sendTaskSuccess("test-token",
            Job903ResultData.builder().result("result_job903").build());
    }

    @Test
    void doesNotSendSuccessWhenInputIsInvalid() {
        ReflectionTestUtils.setField(tasklet, "inputData", "invalid-json");

        assertThrows(JacksonException.class,
            () -> tasklet.execute(mock(StepContribution.class), mock(ChunkContext.class)));

        verifyNoInteractions(sender);
    }
}
