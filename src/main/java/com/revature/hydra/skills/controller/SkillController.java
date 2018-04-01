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

import com.revature.hydra.skills.beans.Skill;
import com.revature.hydra.skills.service.SkillCompositionService;
import com.revature.hydra.skills.transfer.ResponseErrorDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	/*
	 * Caliber controller methods
	 */

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
	public ResponseEntity<Skill> updateSkillCaliber(@Valid @RequestBody Skill skill) {
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

	/*
	 * Minerva controller methods
	 */

	// CREATE
	// creating new curriculum object from information passed from curriculum data
	// transfer object
	@RequestMapping(value = "/api/v2/skill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a skill", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully created Skill information"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot create Skill") })
	public Object createSkill(@RequestBody Skill in) {

		String name = in.getSkillName();
		Boolean active = in.isActive();

		Skill out = new Skill(name, active);
		skillService.save(out);

		if (out == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Skill failed to save."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Skill>(out, HttpStatus.OK);
		}
	}

	// RETRIEVE
	// retrieve skill with given ID
	@RequestMapping(value = "/api/v2/skill/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get a skill of a given ID", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully received Skill information"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot retrieve Skill") })
	public Object retrieveSkill(@PathVariable("id") int ID) {

		Skill out = skillService.findOne(ID);
		if (out == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("No skill found of ID " + ID + "."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Skill>(out, HttpStatus.OK);
		}
	}

	// UPDATE
	// updating an existing skill object with information passed from skill data
	// transfer object
	@RequestMapping(value = "/api/v2/skill", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update a skill", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully updated Skill information"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot update Skill") })
	public Object updateSkillMinerva(@RequestBody Skill in) {

		String name = in.getSkillName();
		Boolean active = in.isActive();

		Skill out = new Skill(name, active);
		skillService.save(out);

		if (out == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Skill failed to save."),
					HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<Skill>(out, HttpStatus.OK);
		}
	}

	// DELETE
	// delete skill with given ID
	@RequestMapping(value = "/api/v2/skill/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete a skill", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully deleted Skill"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot delete Skill") })
	public Object deleteSkill(@PathVariable("id") int ID) {

		skillService.delete(ID);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	// GET ALL
	// retrieve all skills
	@RequestMapping(value = "/api/v2/skill", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve all Skills", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all SKills"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot retrieve Skill") })
	public Object retrieveAllSkills() {

		List<Skill> all = skillService.findAll();
		if (all == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Fetching all skills failed."),
					HttpStatus.NOT_FOUND);
		} else if (all.isEmpty()) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("No skills available."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Skill>>(all, HttpStatus.OK);
		}
	}

	// GET SKILLS according to List of IDs
	@RequestMapping(value = "/api/v2/skill/ids", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve skills by IDs", response = ResponseEntity.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved skills through IDs"),
			@ApiResponse(code = 400, message = "Bad Request, the information recieved maybe invalid"),
			@ApiResponse(code = 500, message = "Cannot retrieve Skills through IDs") })
	public Object retrieveSkillsByIds(@RequestBody List<Integer> in) {
		List<Skill> skills = skillService.findAllById(in);
		if (skills == null) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Fetching all skills through IDs failed."),
					HttpStatus.NOT_FOUND);
		} else if (skills.isEmpty()) {
			return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("No skills available."),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Skill>>(skills, HttpStatus.OK);
		}
	}

}
