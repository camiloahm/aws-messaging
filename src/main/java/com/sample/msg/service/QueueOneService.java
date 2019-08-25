package com.sample.msg.service;

import com.sample.msg.message.QueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class QueueOneService {

    private final SqsMessageQueueService sqsMessageQueueService;
    private final String queueName;

    public QueueOneService(
            @Value("${sqs.name}") String queueName,
            SqsMessageQueueService sqsMessageQueueService) {

        this.sqsMessageQueueService = sqsMessageQueueService;
        this.queueName = queueName;
    }

    public void handle(String message) {
        log.info("Handling started for queue one message: {}", message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Handling stop");
    }

    public void send() {
        QueueMessage queueMessage = new QueueMessage();
        queueMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        queueMessage.setMessage("Example Message For Queue One");
        sqsMessageQueueService.sendMessage(queueName, queueMessage);
    }
}
