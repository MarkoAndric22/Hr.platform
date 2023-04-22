package com.hr.hrplatform.Services;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.CandidateSkill;

public interface CandidateSkillService {
	public CandidateSkill addSkilltoKandidat(Integer id_candidate,Integer id_skill)throws RESTError;
	public CandidateSkill removeSkilltoKandidat(Integer id_candidate,Integer id_skill)throws RESTError;
}
