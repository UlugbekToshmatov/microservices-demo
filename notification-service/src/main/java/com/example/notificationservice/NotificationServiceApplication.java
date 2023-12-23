package com.example.notificationservice;

import com.example.notificationservice.events.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}


	@KafkaListener(topics = "notificationTopic", groupId = "notify")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		log.info("Received notification for Order - {}", orderPlacedEvent.getOrderNumber());
	}

}
