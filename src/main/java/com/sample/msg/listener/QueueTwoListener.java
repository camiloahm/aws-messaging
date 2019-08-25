package com.sample.msg.listener;

import com.sample.msg.service.QueueTwoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueTwoListener {

    private final QueueTwoService queueTwoService;

    public QueueTwoListener(final QueueTwoService queueTwoService) {
        this.queueTwoService = queueTwoService;
    }

    //@SqsListener(value = "queuetwo.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listen(String message, String messageId) {
        log.info("Queue Two message received message : {}, messageId: {} ", message, messageId);
        queueTwoService.handle(message);
    }
}
