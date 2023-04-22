package com.hr.hrplatform.mappers;

import org.springframework.stereotype.Component;

import com.hr.hrplatform.entities.Skill;
import com.hr.hrplatform.entities.dto.SkillDTO;
@Component
public class SkillMapper implements GenericMapper<Skill, SkillDTO> {

	@Override
	public Skill toEntity(SkillDTO dto) {
		
		return new Skill(dto.getName());
	}

	@Override
	public SkillDTO toDto(Skill entity) {
		
		return new SkillDTO (entity.getName());
	}

}
