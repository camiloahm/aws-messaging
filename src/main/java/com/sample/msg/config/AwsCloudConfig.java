package com.sample.msg.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.SnsConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.SqsClientConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.SqsConfiguration;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import({SqsClientConfiguration.class, SqsConfiguration.class})
class AwsCloudConfig {

    @Bean
    @Primary
    AmazonSNS amazonSNS(@Value("${cloud.aws.region.static}") String region) {
        return AmazonSNSClientBuilder.standard().
                withRegion(region)
                .build();
    }

    @Bean
    @Primary
    NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }

    @Bean
    @Primary
    @Qualifier("amazonSQSAsync")
    AmazonSQSAsync amazonSQSAsync(@Value("${cloud.aws.region.static}") String region) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    @Bean
    @Primary
    SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(1);
        factory.setWaitTimeOut(10);
        factory.setQueueMessageHandler(new QueueMessageHandler());
        return factory;
    }
}