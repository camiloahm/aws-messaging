package com.sample.msg.config;

import com.amazonaws.auth.*;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.SqsClientConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.SqsConfiguration;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.*;

@Configuration
@Import({SqsClientConfiguration.class, SqsConfiguration.class})
public class AwsCloudConfig {

    @Bean
    @Primary
    @Qualifier("amazonSQSAsync")
    public AmazonSQSAsync amazonSQSAsync(@Value("${cloud.aws.region.static}") String region) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(1);
        factory.setWaitTimeOut(10);
        factory.setQueueMessageHandler(new QueueMessageHandler());
        return factory;
    }
}