package com.hr.hrplatform.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hrplatform.Services.CandidateService;
import com.hr.hrplatform.Services.CandidateSkillService;
import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.dto.CandidateDTO;
import com.hr.hrplatform.repositories.CandidateRepository;
import com.hr.hrplatform.repositories.CandidateSkillRepository;
import com.hr.hrplatform.repositories.SkillsRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@CrossOrigin("*")
@ApiOperation(value = "")
@ApiResponses(value = { @ApiResponse(code = 0, message = "") })
@RestController
@RequestMapping(path = "/hrplatform/candidate")
public class CandidatesController {

	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	CandidateSkillRepository candidateSkillRepository;
	@Autowired
	CandidateSkillService candidateSkillService;
	@Autowired
	SkillsRepository skillsRepository;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@Valid @RequestBody CandidateDTO candidate,BindingResult result) {
		
		try {
			if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
		}
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.add(candidate));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE,value="/{id}")
	public ResponseEntity<?>removeCandidate(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.deleteCandidate(id));
			
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT,value="/{id}")
	public ResponseEntity<?>modifyCandidate(@PathVariable Integer id,@Valid @RequestBody CandidateDTO candidate){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.modify(id,candidate));
			
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET,value="/name")
	public ResponseEntity<?> getCandidateByName(@RequestParam String name){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.searchCandidateByName(name));
		} catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	@RequestMapping(method = RequestMethod.GET,value="/skill")
	public ResponseEntity<?> getCandidateBySkill(@RequestParam String skill){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.searchCandidateBySkill(skill));
		} catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/{id}")
	public ResponseEntity<?> getCandidateById(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.getById(id));
		} catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getCandidateAll(){
	
			return ResponseEntity.status(HttpStatus.OK).body(candidateService.getAll());
		
		
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/ADDskill")
	public ResponseEntity<?>addSkilltoCandidate(@RequestParam Integer candidate_id,@RequestParam Integer skill_id){
			
		try {
		return ResponseEntity.status(HttpStatus.OK).body((candidateSkillService.addSkilltoKandidat(candidate_id,skill_id)));
		}catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	@RequestMapping(method = RequestMethod.DELETE,value = "/REMOVEskill")
	public ResponseEntity<?>removeSkilltoKandidate(@RequestParam Integer id_candidate,@RequestParam Integer id_skill){
		try {
			return ResponseEntity.status(HttpStatus.OK).body((candidateSkillService.removeSkilltoKandidat(id_candidate, id_skill)));
		}catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/getSkillforCandidate/{id}")
	public ResponseEntity<?> getSkillforCandidate(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(candidateSkillService.getSkillforCandidate(id));
		} catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
}

