package com.hr.hrplatform.Services;

import java.util.List;

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
	public CandidateSkill addSkilltoKandidat(Integer id_candidate, Integer id_skill) throws RESTError {

		if (!candidateRepository.existsById(id_candidate)) {
			throw new RESTError(1, "Candidate not exists");
		}
		if (!skillsRepository.existsById(id_skill)) {
			throw new RESTError(1, "Skill not exists");
		}
		Candidate candidate = candidateRepository.findById(id_candidate).get();
		Skill skill = skillsRepository.findById(id_skill).get();

		List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCandidate(candidate);
		for (CandidateSkill cs : candidateSkills) {
			if (cs.getSkill().equals(skill)) {
				throw new RESTError(1, "Candidate already has this skill");
			}
		}

		CandidateSkill candidateSkill = new CandidateSkill(candidate, skill);

		candidateSkillRepository.save(candidateSkill);
		return candidateSkill;
	}

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
		
		if(!candidateSkillRepository.existsByCandidateAndSkill(candidate, skill)) {
			throw new  RESTError(1, "Candidate and skill are not linked");
		}

		CandidateSkill candidateSkill = candidateSkillRepository.findByCandidateAndSkill(candidate, skill);

		candidateSkillRepository.delete(candidateSkill);

		return candidateSkill;
	}

}
