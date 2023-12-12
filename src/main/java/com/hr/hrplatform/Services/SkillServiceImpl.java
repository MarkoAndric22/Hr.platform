package com.hr.hrplatform.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.Skill;
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

	@Override
	public Optional<Skill> getById(Integer id) throws RESTError {
		Optional<Skill>skills=skillsRepository.findById(id);
		if(skills.isPresent()) {
			return skills;
		}else {
			throw new RESTError(1, "Candidate not exists");
		}
	}

}
