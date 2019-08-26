package com.sample.msg.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class SNSController {

    private final NotificationMessagingTemplate notificationMessagingTemplate;
    private final AmazonSNS amazonSNS;

    @Value("${sns.topic.ARN}")
    private String snsTopicDemoARN;




    @RequestMapping(value = "/sendMessageQueue", method = RequestMethod.POST)
    public @ResponseBody
    void write(@RequestBody String notificationData) {
        try {

            log.info("===============================================");
            log.info("Getting Started with Amazon SQS Standard Queues");
            log.info("===============================================\n");

            log.info("Sending a message to MyQueue.\n");
            notificationMessagingTemplate.sendNotification(snsTopicDemoARN,
                    "{\"mobileno\": \"+919996019206\", \"message\": \"Hello Camilo! How have you been doing? Hope you are doing great. Good bye!\"}", "subject");
            PublishRequest publishRequest = new PublishRequest(snsTopicDemoARN, notificationData, "test");
            PublishResult publishResult = this.amazonSNS.publish(publishRequest);
            log.info("Message Sent.\n");

        } catch (final Exception ase) {
            log.error("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
        }
    }
}
