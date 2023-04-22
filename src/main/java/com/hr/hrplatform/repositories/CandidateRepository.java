package com.hr.hrplatform.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hr.hrplatform.entities.Candidate;
import com.hr.hrplatform.entities.CandidateSkill;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Integer>{
	
	List<Candidate> findByName(String name);
	
	List<Candidate> findByNameIgnoreCase(String name);

	List<Candidate> findAllCandidatesBycandidateSkills(CandidateSkill skill);
	
	Candidate findByEmail(String email);
	Boolean existsByEmail(String email);
	
}
