package com.revature.hydra.skills.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.hydra.skills.beans.SimpleSkill;

@Service
public class SkillRepositoryMessagingService {

	@Autowired
	private SkillRepositoryRequestDispatcher skillRepositoryRequestDispatcher;

	/**
	 * Receives a message from the single SimpleSkill RabbitMQ queue, parses the
	 * message string as a JsonObject, and passes it to the request dispatcher.
	 * 
	 * @param message
	 *            The message received from the messaging queue
	 * @return The simple skill returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.hydra.repos.skills")
	public SimpleSkill receiveSingleSimpleSkillRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();

		return skillRepositoryRequestDispatcher.processSingleSimpleSkillRequest(request);
	}

	/**
	 * Receives a message from the list SimpleSkills RabbitMQ queue, parses the
	 * message string as a JsonObject, and passes it to the request dispatcher.
	 * 
	 * @param message
	 *            The message received from the messaging queue
	 * @return The list of simple skills returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.hydra.repos.skills.list")
	public List<SimpleSkill> receiveListSimpleSkillRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();

		return skillRepositoryRequestDispatcher.processListSimpleSkillRequest(request);
	}

}
