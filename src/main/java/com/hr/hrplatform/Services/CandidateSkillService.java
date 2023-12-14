package com.hr.hrplatform.Services;

import java.util.List;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.Skill;

public interface CandidateSkillService {
	public CandidateSkill addSkilltoKandidat(Integer id_candidate,Integer id_skill)throws RESTError;
	public CandidateSkill removeSkilltoKandidat(Integer id_candidate,Integer id_skill)throws RESTError;
	public List<Skill> getSkillforCandidate(Integer id)throws RESTError;
}
