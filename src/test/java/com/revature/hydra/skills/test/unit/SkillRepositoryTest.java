package com.revature.hydra.skills.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.hydra.skills.beans.SimpleSkill;
import com.revature.hydra.skills.data.SkillRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SkillRepositoryTest {

	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private static final Logger log = Logger.getLogger(SkillRepositoryTest.class);

	private static final String SKILL_COUNT = "select count(skill_id) from hydra_skill";
	private static final String ACTIVE_SKILL = "select count(skill_id) from hydra_skill WHERE IS_ACTIVE = 1;";

	@Test
	public void test() {
		assertTrue(true);
	}

	@Test
	public void findOne() {
		log.info("Testing findOne method from SkillDAO");
		SimpleSkill myCat = skillRepository.findOne(1);
		assertNotNull(myCat);
		assertTrue(skillRepository.findOne(1) instanceof SimpleSkill);
		assertEquals(skillRepository.findOne(1).toString(), "Java");
	}

	@Test
	public void testAll() {
		int expected = skillRepository.findAll().size();
		int actual = jdbcTemplate.queryForObject(SKILL_COUNT, Integer.class);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAllActive() {
		log.info("Testing findAllActive from SkillDAO");
		int expected = skillRepository.findByActiveOrderBySkillIdAsc(true).size();
		int actual = jdbcTemplate.queryForObject(ACTIVE_SKILL, Integer.class);
		assertEquals(expected, actual);
	}

	@Test
	public void update() {
		log.info("Testing update from SkillDAO");
		String skillName = "Revature Pro";
		SimpleSkill myCat = skillRepository.findOne(1);
		myCat.setSkillName(skillName);
		skillRepository.save(myCat);
		assertEquals(skillName, myCat.getSkillName());
	}

	@Test
	public void save() {
		log.info("Testing save method from SkillDAO");
		SimpleSkill newCat = new SimpleSkill("Underwater Basket Weaving", false);
		Long before = jdbcTemplate.queryForObject(SKILL_COUNT, Long.class);
		skillRepository.saveAndFlush(newCat);
		Long after = jdbcTemplate.queryForObject(SKILL_COUNT, Long.class);
		assertEquals(++before, after);
	}

	@Test
	public void delete() {
		log.info("Testing delete method from SkillDAO");
		skillRepository.delete(skillRepository.findOne(1));
	}

}
