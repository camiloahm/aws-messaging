package com.sample.msg.listener;

import com.sample.msg.service.QueueOneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@EnableAsync
public class QueueOneListener {

    private final QueueOneService oneService;

    public QueueOneListener(final QueueOneService oneService) {
        this.oneService = oneService;
    }

    @Async
    @SqsListener(value = "${sqs.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listen(Acknowledgment acknowledgment, String message, String messageId) {
        log.info("Queue One message received message : {}, messageId: {} ", message, messageId);
        if (message.contains("bla")) {
            throw new RuntimeException();
        }
        oneService.handle(message);
        try {
            acknowledgment.acknowledge().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
