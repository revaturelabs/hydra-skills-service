package com.revature.hydra.skills.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Skill;
import com.revature.hydra.skills.service.SkillCompositionService;

@RestController
// @PreAuthorize("isAuthenticated()")
@CrossOrigin
public class SkillController {
	private static final Logger log = Logger.getLogger(SkillController.class);
	private SkillCompositionService skillService;

	@Autowired
	public void setSkillService(SkillCompositionService skillService) {
		this.skillService = skillService;
	}

	// Calls a method that will return all ACTIVE Skills. This will NOT return
	// INACTIVE Skills.
	@RequestMapping(value = "/skill/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public ResponseEntity<List<Skill>> findAllActive() {
		log.debug("Getting skills");
		List<Skill> skills = skillService.findAllActive();
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}

	// Calls a method that will return all Skills both ACTIVE and INACTIVE.
	// Intended to be used by VP only.
	@RequestMapping(value = "/vp/skill", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<List<Skill>> findAll() {
		log.debug("Getting skills");
		List<Skill> skills = skillService.findAll();
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}

	// Calls a method that will find a skill based on id.
	@RequestMapping(value = "/skill/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING','PANEL')")
	public ResponseEntity<Skill> findSkillById(@PathVariable int id) {
		log.debug("Getting skill: " + id);
		Skill skill = skillService.findOne(id);
		log.info(skill.toString());
		return new ResponseEntity<>(skill, HttpStatus.OK);
	}

	// Calls a method that will update a skill's name or activity
	@RequestMapping(value = "/vp/skill/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP')")
	public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) {
		skillService.update(skill);
		return new ResponseEntity<>(skill, HttpStatus.OK);
	}

	// Calls a method that creates a new Skill
	@RequestMapping(value = "/vp/skill", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	// @PreAuthorize("hasAnyRole('VP')")
	public ResponseEntity<Skill> saveSkill(@Valid @RequestBody Skill skill) {
		skillService.save(skill);
		return new ResponseEntity<>(skill, HttpStatus.CREATED);
	}

}
