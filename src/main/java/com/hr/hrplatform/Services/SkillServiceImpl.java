package com.hr.hrplatform.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.dto.SkillDTO;
import com.hr.hrplatform.mappers.SkillMapper;
import com.hr.hrplatform.repositories.SkillsRepository;
@Service
public class SkillServiceImpl implements SkillService {
	@Autowired
	SkillsRepository skillsRepository;
	@Autowired
	SkillMapper skillMapper;

	@Override
	public SkillDTO add(SkillDTO skill)throws RESTError {
		if(skillsRepository.existsByName(skill.getName())) {
			throw new RESTError(1, "Skill already exists");
		}
		return skillMapper.toDto(skillsRepository.save(skillMapper.toEntity(skill)));
	}

}
