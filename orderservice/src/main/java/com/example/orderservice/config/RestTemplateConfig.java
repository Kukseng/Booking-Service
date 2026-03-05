package com.example.orderservice.config;

import com.example.bookingservice.event.BookingEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableKafka
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, BookingEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, BookingEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(new org.springframework.kafka.listener.DefaultErrorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
