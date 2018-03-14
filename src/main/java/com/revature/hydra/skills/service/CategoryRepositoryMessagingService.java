package com.revature.hydra.skills.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.beans.SimpleCategory;

@Service
public class CategoryRepositoryMessagingService {

	@Autowired
	private CategoryRepositoryRequestDispatcher categoryRepositoryRequestDispatcher;

	/**
	 * Receives a message from the single SimpleCategory RabbitMQ queue, parses the
	 * message string as a JsonObject, and passes it to the request dispatcher.
	 * 
	 * @param message
	 *            The message received from the messaging queue
	 * @return The simple category returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.hydra.repos.skills")
	public SimpleCategory receiveSingleSimpleCategoryRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();

		return categoryRepositoryRequestDispatcher.processSingleSimpleCategoryRequest(request);
	}

	/**
	 * Receives a message from the list SimpleCategories RabbitMQ queue, parses the
	 * message string as a JsonObject, and passes it to the request dispatcher.
	 * 
	 * @param message
	 *            The message received from the messaging queue
	 * @return The list of simple categories returned by the request dispatcher
	 */
	@RabbitListener(queues = "revature.hydra.repos.skills.list")
	public List<SimpleCategory> receiveListSimpleCategoryRequest(String message) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(message);
		JsonObject request = element.getAsJsonObject();

		return categoryRepositoryRequestDispatcher.processListSimpleCategoryRequest(request);
	}

}
