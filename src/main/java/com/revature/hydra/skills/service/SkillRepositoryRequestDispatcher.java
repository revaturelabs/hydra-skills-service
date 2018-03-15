package com.revature.hydra.skills.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.revature.beans.SimpleSkill;
import com.revature.hydra.skills.data.SkillRepository;

/**
 * Processes messages from other services. FindOne, findOneBySkillName, and
 * findAll are requests from other services needed to construct their complex
 * beans for the front end.
 * 
 */
@Service
public class SkillRepositoryRequestDispatcher {

	@Autowired
	private SkillRepository skillRepository;

	/**
	 * Handle a messaging request for a simple skill.
	 * 
	 * @param request
	 *            The JsonObject that defines the parameters for the simple skill
	 *            to be returned
	 * @return A simple skill according to the parameters in the request
	 */
	public SimpleSkill processSingleSimpleSkillRequest(JsonObject request) {
		SimpleSkill result = null;
		String methodName = request.get("methodName").getAsString();

		if (methodName.equals("findOne")) {
			int skillId = request.get("skillId").getAsInt();
			result = skillRepository.findOne(skillId);
		} else if (methodName.equals("findOneBySkillName")) {
			String skillName = request.get("skillName").getAsString();
			result = skillRepository.findOneBySkillName(skillName);
		}

		return result;
	}

	/**
	 * Handle a messaging request for a list of simple skills.
	 * 
	 * @param request
	 *            The JsonObject that defines the parameters for the list of simple
	 *            skills to be returned
	 * @return A list of simple skills according to the parameters in the request
	 */
	public List<SimpleSkill> processListSimpleSkillRequest(JsonObject request) {
		List<SimpleSkill> result = null;
		String methodName = request.get("methodName").getAsString();

		if (methodName.equals("findAll")) {
			result = skillRepository.findAll();
		}

		return result;
	}

}
