package com.hr.hrplatform.Services;

import java.util.Optional;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Skill;
import com.hr.hrplatform.entities.dto.SkillDTO;

public interface SkillService {
	public SkillDTO add(SkillDTO skill)throws RESTError;
	public Optional<Skill> getById(Integer id)throws RESTError;
}
