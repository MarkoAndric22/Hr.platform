package com.hr.hrplatform.Services;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.dto.SkillDTO;

public interface SkillService {
	public SkillDTO add(SkillDTO skill)throws RESTError;

}
