package com.revature.hydra.skills.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.beans.Skill;
import com.revature.beans.SimpleSkill;
import com.revature.hydra.skills.data.SkillRepository;

public class SkillCompositionService {
	@Autowired
	private SkillRepository skillRepository;
	// commented out because it was unused
	// @Autowired
	// private SkillCompositionMessagingService
	// skillCompositionMessagingService;
	private static final Logger log = Logger.getLogger(SkillCompositionService.class);

	/**
	 * . Finds one simplified bean from the service database and composes it into a
	 * complex bean required by the front end.
	 *
	 * @param int
	 *            the id of a Skill
	 * @return A Skill object
	 */
	public Skill findOne(int skillId) {
		log.info("Finding one simple skill");
		SimpleSkill basis = skillRepository.findOne(skillId);
		Skill result = composeSkill(basis);
		return result;
	}

	/**
	 * . Finds all simplified beans from the service database and composes them into
	 * complex beans required by the front end.
	 *
	 * @return A List of Skill objects ordered by skill name Ascending
	 */
	public List<Skill> findAll() {
		List<SimpleSkill> basis = skillRepository.findAllByOrderBySkillNameAsc();
		List<Skill> result = composeListOfSkill(basis);

		return result;
	}

	/**
	 * . Finds all simplified beans from the service database and composes them into
	 * complex beans required by the front end.
	 *
	 * @return A List of Skill objects ordered by their id Ascending
	 */
	public List<Skill> findAllActive() {
		List<SimpleSkill> basis = skillRepository.findByActiveOrderBySkillIdAsc(true);
		List<Skill> result = composeListOfSkill(basis);

		return result;
	}

	public List<Skill> findAllById(List<Integer> ids) {
		List<SimpleSkill> basis = skillRepository.findAll((Iterable<Integer>) ids);
		List<Skill> result = composeListOfSkill(basis);

		return result;
	}

	/**
	 * . Given a Skill create a simple version and saves it
	 *
	 * @param A
	 *            Skill object to save
	 */
	public void save(Skill skill) {
		SimpleSkill toSave = new SimpleSkill(skill);
		skillRepository.save(toSave);
	}

	/**
	 * . Given a Skill create a simple version and use it to update
	 *
	 * @param A
	 *            Skill object to update
	 */
	public void update(Skill skill) {
		SimpleSkill toSave = new SimpleSkill(skill);
		skillRepository.save(toSave);
	}

	/**
	 * . Delete a Skill
	 *
	 * @param int
	 *            the id of a Skill
	 */
	public void delete(int skillId) {
		skillRepository.delete(skillId);
	}

	private List<Skill> composeListOfSkill(List<SimpleSkill> src) {
		List<Skill> dest = new LinkedList<Skill>();

		for (SimpleSkill curr : src) {
			dest.add(new Skill(curr));
		}

		return dest;
	}

	private Skill composeSkill(SimpleSkill src) {
		Skill dest = new Skill(src);
		return dest;
	}

}
