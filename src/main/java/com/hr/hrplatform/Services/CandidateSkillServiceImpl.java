package com.hr.hrplatform.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hrplatform.controllers.util.RESTError;
import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.Skill;
import com.hr.hrplatform.repositories.CandidateRepository;
import com.hr.hrplatform.repositories.CandidateSkillRepository;
import com.hr.hrplatform.repositories.SkillsRepository;

@Service
public class CandidateSkillServiceImpl implements CandidateSkillService {
	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	SkillsRepository skillsRepository;
	@Autowired
	CandidateSkillRepository candidateSkillRepository;

	

	@Override
	public CandidateSkill removeSkilltoKandidat(Integer id_candidate, Integer id_skill) throws RESTError {
		if (!candidateRepository.existsById(id_candidate)) {
			throw new RESTError(1, "Candidate not exists");
		}
		if (!skillsRepository.existsById(id_skill)) {
			throw new RESTError(1, "Skill not exists");
		}
		Candidate candidate = candidateRepository.findById(id_candidate).get();
		Skill skill = skillsRepository.findById(id_skill).get();

		if (!candidateSkillRepository.existsByCandidateAndSkill(candidate, skill)) {
			throw new RESTError(1, "Candidate and skill are not linked");
		}

		CandidateSkill candidateSkill = candidateSkillRepository.findByCandidateAndSkill(candidate, skill);

		candidateSkillRepository.delete(candidateSkill);

		return candidateSkill;
	}

	@Override
	public List<Skill> getSkillforCandidate(Integer id) throws RESTError {
		Candidate candidate = candidateRepository.findById(id).get();
		List<CandidateSkill> candidateSkill = candidateSkillRepository.findByCandidate(candidate);
		List<Skill> skills = skills = candidateSkill.stream().map(CandidateSkill::getSkill)
				.collect(Collectors.toList());
		return skills;
	}

	@Override
	public List<Skill> getSkillForCandidateDontHave(Integer id) throws RESTError {
		Candidate candidate = candidateRepository.findById(id).get();
		List<Skill>skills=new ArrayList<Skill>();
		for(Skill skill:skillsRepository.findAll()) {
			if(!getSkillforCandidate(id).contains(skill)) {
				skills.add(skill);
			}
		}
		return skills;
	}

	@Override
	public Candidate addSkilltoKandidat(Integer id_candidate, List<Skill> skills) throws RESTError {
		
		Candidate candidate=candidateRepository.findById(id_candidate).get();
		for(Skill s:skills) {
			
			candidateSkillRepository.save(new CandidateSkill(candidate,s));
	}
		
		return candidate;
	} 
}
