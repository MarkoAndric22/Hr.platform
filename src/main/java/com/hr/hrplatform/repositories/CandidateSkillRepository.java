package com.hr.hrplatform.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;
import com.hr.hrplatform.entities.Skill;

@Repository
public interface CandidateSkillRepository extends CrudRepository<CandidateSkill, Integer> {

	List<CandidateSkill> findBySkill(Skill skill);

	List<CandidateSkill> findByCandidate(Candidate candidate);

	List<CandidateSkill> findBySkillName(String skillName);

	CandidateSkill findByCandidateAndSkill(Candidate candidate, Skill skill);

	Boolean existsByCandidateAndSkill(Candidate candidate, Skill skill);
}
