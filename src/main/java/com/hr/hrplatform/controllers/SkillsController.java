package com.hr.hrplatform.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hrplatform.Services.SkillService;
import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.dto.SkillDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@CrossOrigin("*")
@ApiOperation(value = "")
@ApiResponses(value = { @ApiResponse(code = 0, message = "") })
@RestController
@RequestMapping(path = "/hrplatform/skill")
public class SkillsController {

	@Autowired
	SkillService skillService;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?>addSkill(@Valid @RequestBody SkillDTO skill, BindingResult result){
		
		try {
			if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
		}
			return ResponseEntity.status(HttpStatus.OK).body(skillService.add(skill));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/{id}")
	public ResponseEntity<?> getSkillById(@RequestParam Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(skillService.getById(id));
		} catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}

}
