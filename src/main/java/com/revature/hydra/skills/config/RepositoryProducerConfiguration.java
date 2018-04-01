// package com.revature.hydra.skills.config;

// import org.springframework.amqp.core.AmqpTemplate;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.revature.hydra.skills.service.SkillCompositionService;

// @Configuration
// public class RepositoryProducerConfiguration {

// 	@Bean
// 	public AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
// 		return new RabbitTemplate(factory);
// 	}

// 	@Bean
// 	public SkillCompositionService panelFeedbackCompositionService() {
// 		return new SkillCompositionService();
// 	}

// }
