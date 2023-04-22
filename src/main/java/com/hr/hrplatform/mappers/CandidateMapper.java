package com.hr.hrplatform.mappers;

import org.springframework.stereotype.Component;

import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.dto.CandidateDTO;
@Component
public class CandidateMapper implements GenericMapper<Candidate, CandidateDTO> {

	@Override
	public Candidate toEntity(CandidateDTO dto) {
		
		return new Candidate(dto.getName(),dto.getDate_of_birth(),dto.getContact_number(),dto.getEmail());
	}

	@Override
	public CandidateDTO toDto(Candidate entity) {
	
		return new CandidateDTO(entity.getName(),entity.getDate_of_birth(),entity.getContact_number(),entity.getEmail());
	}

}
