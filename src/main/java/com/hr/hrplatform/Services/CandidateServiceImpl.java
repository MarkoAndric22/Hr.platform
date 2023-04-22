package com.hr.hrplatform.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.dto.CandidateDTO;
import com.hr.hrplatform.mappers.CandidateMapper;
import com.hr.hrplatform.repositories.CandidateRepository;
import com.hr.hrplatform.repositories.CandidateSkillRepository;
import com.hr.hrplatform.repositories.SkillsRepository;
@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	CandidateMapper candidateMapper;
	@Autowired
	SkillsRepository skillsRepository;
	@Autowired
	CandidateSkillRepository candidateSkillRepository;


	@Override
	public CandidateDTO add(CandidateDTO candidate) throws RESTError {
		if(candidateRepository.existsByEmail(candidate.getEmail())) {
			throw new RESTError(1, "Candidate already exists");
		}
		return candidateMapper.toDto(candidateRepository.save(candidateMapper.toEntity(candidate)));
	}

	@Override
	public CandidateDTO modify(Integer id,CandidateDTO candidate) throws RESTError {
		if(candidateRepository.existsById(id)) {
			return candidateMapper.toDto(candidateRepository.save(candidateMapper.toEntity(candidate)));
		}
		throw new RESTError(1, "Candidate not exists");
		
      
	}

	@Override
	public Candidate deleteCandidate(Integer id) throws RESTError {
		Optional<Candidate>candidate= candidateRepository.findById(id);
		if(!candidate.isEmpty()) {
			candidateRepository.delete(candidate.get());
			return candidate.get();
		}
		throw new RESTError(1, "Candidate not exists");
	}

	@Override
	public List<CandidateDTO> searchCandidateByName(String name) throws RESTError {

		    List<Candidate> candidates = candidateRepository.findByNameIgnoreCase(name);
		    if(candidates.isEmpty()) {
		    	throw new RESTError(1, "Candidate not exists");
		    }
		    return candidates.stream().map(candidateMapper::toDto).collect(Collectors.toList());
		}

	

	@Override
	public List<CandidateDTO> searchCandidateBySkill(String skillName) throws RESTError {
		 List<CandidateSkill> candidateSkills = candidateSkillRepository.findBySkillName(skillName);
		 List<Candidate> candidates = candidateSkills.stream()
		            .map(CandidateSkill::getCandidate)
		            .collect(Collectors.toList());
		 if(candidates.isEmpty()) {
		    	throw new RESTError(1, "Candidate not exists");
		 }
		return candidates.stream().map(candidateMapper::toDto).collect(Collectors.toList());
	}

}
